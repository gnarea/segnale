# Segnale Architecture

## Overview

Segnale is a local-first Android app (with future iOS/Desktop support) that extracts actionable signals from newsletters using LLM-based processing. The architecture follows Clean Architecture principles with clear separation between business logic, data access, and presentation layers.

## Architecture Pattern

**Clean Architecture** organised in three layers:

- **Domain Layer**: Pure business logic, use cases, and domain models. No platform dependencies, fully testable.
- **Data Layer**: Repository implementations, API clients, database access. Implements interfaces defined in domain layer.
- **Presentation Layer**: UI built with Compose Multiplatform. Observes domain layer via reactive streams.

**Key Principle**: Dependencies point inward. Data and Presentation depend on Domain, never the reverse.

## Technology Stack

### Core Platform

- **Kotlin Multiplatform (KMP)**: Shared business logic across Android, iOS, Desktop.
- **Compose Multiplatform**: Shared declarative UI framework.
- **Gradle 8.x**: Build system with Kotlin DSL and version catalogs.

### Data & Storage

- **Room KMP**: Local database for signals, tasks, email metadata, and preferences.
- **Gmail API**: Email access via official Google API (not IMAP).
- **kotlinx.serialization**: JSON serialization for API responses.

### LLM Integration

- **Anthropic Java SDK**: Initial LLM provider for classification and extraction.
- **Custom abstraction layer**: Provider-agnostic interface to support multiple LLM vendors.

### Infrastructure

- **Koin**: Lightweight dependency injection.
- **kotlinx.coroutines**: Async/reactive programming.
- **WorkManager**: Background email sync (Android-specific).

### Testing & Quality

- **kotlin-test + Kotest**: Cross-platform testing.
- **Turbine**: Flow/reactive stream testing.
- **MockK**: Mocking framework.
- **Detekt + ktlint**: Static analysis and code formatting.

## Module Organisation

Project generated via Android Studio KMP wizard with the following structure:

- **composeApp/**: Single KMP module containing both business logic and UI
  - `commonMain/`: Platform-agnostic code (domain layer, UI components, LLM abstraction)
  - `androidMain/`: Android-specific implementations (Gmail API, Room driver, Android app entry point)
  - `iosMain/`: iOS-specific implementations
  - `jvmMain/`: Desktop-specific implementations (JVM target)
  - `commonTest/`: Shared unit tests

- **iosApp/**: iOS application wrapper (Xcode/Swift project)
  - Contains iOS app entry point and configuration
  - Links to ComposeApp framework generated from `composeApp/iosMain`

## Core Components

### Domain Layer (Platform-Agnostic)

**EmailSource**: Abstraction for email access. Provides operations to fetch unread emails, mark as read, and archive. Implementation lives in `androidMain` using Gmail API.

**SignalExtractor**: Orchestrates LLM-based processing:

- Classifies emails (human/newsletter/transactional/spam)
- Extracts events from newsletters
- Aggregates signals across multiple newsletter sources
- Future: Multi-tier urgency classification

**SignalStorage**: Persistence abstraction for extracted signals and feed data. Uses Room KMP for local storage.

**LLMProvider**: Abstract interface for LLM operations (classification, summarization, signal extraction). Hides provider details behind consistent API.

### LLM Abstraction Design

**Purpose**: Decouple application logic from specific LLM vendors (Anthropic, OpenAI, Gemini, local models).

**Key Operations**:

- **classify**: Determine email category (human vs newsletter vs transactional).
- **summarize**: Generate concise summaries of newsletter content.
- **extractSignals**: Parse newsletters to identify events, topics, and relationships.

**Provider Implementations**: Each vendor implements the same interface with provider-specific SDK integration. Switching providers is a configuration change via dependency injection.

**Rationale**:

- Enables A/B testing different LLM providers.
- Future-proofs against vendor lock-in.
- Supports hybrid approaches (local + remote LLMs).
- Simplifies testing via mock providers.

### Data Layer (Platform-Specific)

**Gmail Integration (androidMain)**: Implements EmailSource using Google's official Gmail API. Requires OAuth 2.0 authentication with GMAIL_READONLY and GMAIL_MODIFY scopes. Handles rate limiting via batch operations.

**Room Database (androidMain)**: Implements SignalStorage with entities for signals, events, and metadata. Platform driver instantiated in Android source set, queries defined in common layer.

**Anthropic Provider (commonMain)**: Initial LLM implementation using Anthropic Java SDK. Maps domain requests/responses to provider-specific message formats.

## Data Flow

1. **Background Sync**: WorkManager triggers periodic email synchronization (e.g., every 15 minutes).

2. **Email Fetching**: EmailSource fetches unread emails via Gmail API.

3. **Classification**: SignalExtractor uses LLMProvider to classify each email.

4. **Signal Extraction**: For newsletters, LLMProvider extracts structured events (title, description, topics).

5. **Aggregation**: SignalExtractor matches events across newsletter sources to identify common signals.

6. **Persistence**: Extracted signals saved to Room database via SignalStorage.

7. **Feed Display**: UI observes SignalStorage feed stream (reactive Flow) and renders sorted by relevance.

8. **Email Management**: User interactions trigger EmailSource to mark emails as read and archive.

## Email Strategy

**Gmail API over IMAP**: Google-recommended approach with better reliability, feature access, and integration. IMAP considered legacy for Gmail access.

**Selective Fetching**: Only fetch unread emails to minimize API calls and processing overhead.

**Batch Operations**: Use Gmail API batch endpoints to reduce rate limit impact when marking multiple emails as read.

## Background Processing

**WorkManager**: Android's recommended approach for deferrable, guaranteed background work. Schedules periodic email sync with system-optimised intervals.

**Constraints**: Sync only when device has network connectivity and sufficient battery. Respects Android battery optimization settings.

**Sync Logic**: Encapsulated in use case pattern to keep WorkManager implementation thin and testable.

## Extensibility

### MVP → V1: Multi-Tier Urgency

- Add UrgencyClassifier component to assess signal time sensitivity.
- Extend domain models with urgency metadata.
- Introduce TaskManager for actionable items with due dates.
- Notification service for high-urgency signals.

### V1 → V2: Multi-Source Aggregation

- Generalise EmailSource to ContentSource interface.
- Add RedditSource, TwitterSource, etc. implementing same abstraction.
- Cross-platform event matching in SignalExtractor.
- Unified feed aggregation regardless of content source.

**Design Decision**: Architecture designed around interfaces rather than concrete implementations. New sources or LLM providers integrate without modifying existing code.

## Plugin Architecture

**ContentSource Abstraction**: Extensible interface for integrating custom content sources beyond built-in protocols (email, RSS, BlueSky).

**Plugin Contract**:
- **Content Fetching**: Standard interface for retrieving content from external sources.
- **Event Extraction**: Transform source-specific formats to unified event model (title, summary, timestamp, metadata).
- **Profile Association**: Hook for mapping plugin content to user-defined profiles.
- **Custom Classification**: Optional override for source-specific urgency and categorization logic.

**Integration Pattern**: Plugins implement the ContentSource interface and register via Koin dependency injection. Once registered, plugin events participate in the core aggregation pipeline alongside built-in sources - deduplicated, matched across sources, and ranked by the same urgency/relevance algorithms.

**Use Cases**:
- Slack workspace monitoring with keyword filtering.
- Custom corporate tool integrations.
- Niche platforms without official API support.
- Proprietary newsletter formats requiring specialized parsing.

**Design Rationale**: First-class plugin support from V1 enables custom integrations without modifying core codebase. Users can extend Segnale to their specific workflows while benefiting from existing aggregation and presentation logic.

## Security & Privacy

**API Key Management**: Store sensitive credentials in platform-specific secure storage (Android Keystore, iOS Keychain). Never commit to version control.

**Local-First**: All user data persists locally. Only newsletter content sent to LLM APIs for processing.

**OAuth Scope Minimisation**: Request minimal Gmail API scopes necessary (read + modify, no full access).

**Future Anonymisation**: V1 may introduce local LLM pre-processing to remove PII before remote API calls.

## CI/CD

**GitHub Actions** for continuous integration:

- Automated testing on every PR.
- Static analysis (Detekt) and formatting checks (ktlint).
- Build verification for all platforms.
- Release automation: APK/AAB signing and publishing on tagged commits.

## Open Questions

### MVP Critical

Questions requiring resolution before initial launch:

- **Event Matching** *(Feature 1: Multi-Source Event Aggregation)*: Algorithm for identifying "same event" across different newsletters (semantic similarity vs keyword matching vs embedding-based clustering?).
- **Newsletter Parsing** *(Feature 1: Multi-Source Event Aggregation)*: Single-topic vs multi-topic extraction strategy. How to handle cross-source matching with title variations?
- **Task Expiry** *(Feature 3: Dynamic Task Management)*: Default expiry windows per urgency tier (FYI world news = 7 days, AI releases = 30 days?). User-configurable or learned from behaviour?
- **Behaviour Learning** *(Feature 6: Behaviour Learning & Feedback)*: Cold-start problem - how much manual configuration needed before AI takes over? Explicit prompts vs passive observation for feedback collection?
- **Source Management** *(Feature 5: Intelligent Content Classification)*: Gmail API reliability for processing/archiving - fallback if operations fail?
- **Sync Frequency** *(Background Processing)*: Optimal balance between freshness and battery impact.

### V1 Features

Questions aligned with V1 feature development:

- **Urgency Thresholds** *(Feature 7: Urgency Tiers)*: Concrete criteria for multi-tier classification and notification triggers.
- **Calendar Integration** *(Feature 4: Context-Aware Feed Presentation)*: Privacy model - what calendar data accessed? Local processing only? How reliably can attention be inferred from calendar events?
- **Local LLM Choice** *(Privacy Enhancement)*: Preferred approach for on-device anonymisation (ONNX, Ollama, custom?).
- **Plugin Security** *(Feature 8: Extensible Source Architecture)*: Sandboxing third-party plugins vs trusting user-installed code? Plugin discovery mechanism (centralized registry vs manual installation)?

### Long-term

Future considerations for multi-source expansion:

- **RSS Polling Strategy** *(Feature 8: Extensible Source Architecture)*: Optimal polling frequency vs resource usage for high-volume RSS feeds when multi-source support is added.
