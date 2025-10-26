# Segnale - Signal Extraction System

## Core Concept

An intelligent signal extraction system that cuts through information noise to surface what matters when it matters. **Not an email client** - leaves human correspondence untouched while extracting signal from automated content, newsletters, and notifications.

## Tech Stack

**Cross-platform Foundation:**
- Kotlin Multiplatform (KMP) for shared business logic
- Native Android UI (Jetpack Compose)
- iOS expansion path via shared KMP core

**Architecture & Storage:**
- Fully local application (runs on user's device)
- Local database for tasks, preferences, and email metadata
- IMAP for email access (standard protocol, Gmail-specific features if needed)

**LLM Strategy:**
- Remote LLM APIs: Classification, summarization, signal extraction via third-party inference APIs (OpenAI, Anthropic, etc.)
- Local LLM: Data anonymization before sending to remote services

**Email Handling:**
- Human-sent emails: Untouched (remain unread in Gmail)
- Automated emails: Marked read and archived
- Read via IMAP, modify state via IMAP (Gmail-specific if necessary)

## MVP: Multi-Source Signal Aggregation

**Core Feature:**
Aggregate high-signal coverage of the same event/topic across multiple newsletter sources into a single entry with unified summary.

**Key Capabilities:**
- Parse newsletters and identify distinct topics/events within each
- Detect when multiple sources cover the same event (signal correlation)
- Generate combined summary from all sources
- Present as unified feed entries with signal strength indicators
- Allow drill-down to full articles

**Example:** 
Major AI model release covered by 5 different newsletters → Single feed entry with aggregated signal from all sources, eliminating redundant noise.

**Technical Components:**
- IMAP email fetching
- Email classification (newsletter vs transactional vs human)
- Topic/event extraction from newsletter content
- Cross-source event matching
- Multi-document summarization (remote LLM API)
- Feed UI with ranking algorithm
- Local storage and processing with remote LLM API calls as needed

**Out of Scope for MVP:**
- Multi-tier urgency/notification system
- Task creation from transactional emails
- Social media sources
- Complex signal prioritization logic

## Version 1: Full Signal Management

**Add Multi-Tier Urgency System:**

**Urgency Levels:**
1. **Immediate** - Push notification instantly (critical actions)
2. **Hours** - Deferred notification with timeout (needs attention today)
3. **Days/Weeks** - Passive until app opened (with timeout for extended absence)
4. **FYI Only** - No notification, visible in feed (informational)
5. **Algorithmic Ranking** - Priority-based feed positioning (newsletters, low-urgency items)

**Task Management:**
- Automated task creation from emails requiring action
- Manual task creation capability
- Duplicate detection (multiple emails about same task)
- Due date assignment based on urgency classification

**Email Type Handling:**
- **Human emails**: Untouched, no processing
- **Transactional emails**: 
  - Acknowledgments → FYI-only entry
  - Action required → Task creation with appropriate urgency
- **Newsletters**: Event aggregation with algorithmic ranking

**Notification Strategy:**
- Immediate push for highest urgency
- Timeout-based notifications for deferred items
- Absence detection (if app not opened for extended period, escalate notifications)

**Feed Design:**
- Signal-strength sorted intelligent feed
- Replaces noise-filled dumb-scrolling with curated high-signal content
- Long-form articles summarized with option to read full
- Clear visual hierarchy by signal strength and urgency

## Version 2: Social Media Integration

**Expand Sources Beyond Email:**
- Reddit posts
- Twitter/X
- Other social platforms

**Cross-Source Aggregation:**
Event aggregation includes both newsletter coverage AND social media discussions about the same topic.

**Example:**
AI model release → Aggregated view includes newsletter analyses + relevant Reddit threads + Twitter discussions.

**Technical Additions:**
- API integrations for social platforms
- Cross-platform event matching
- Unified feed across all sources

## Design Principles

1. **Local-First**: App runs entirely on user's device, no proprietary cloud back-end or API
2. **Attention-First**: Optimize for user's cognitive bandwidth, not completeness
3. **Privacy**: Anonymize personal data before remote LLM API processing when possible
4. **Offline-Capable**: Task management and core features work offline
5. **No Replacement**: Gmail remains primary email interface for human correspondence
6. **Progressive Enhancement**: Each version adds capability without breaking prior features

## Open Questions

- Gmail-specific IMAP features: reliability of archive operation via IMAP
- Event matching algorithm: How to determine "same event" across diverse sources
- User preference learning: Manual training vs automatic adaptation
- Newsletter topic extraction: Single vs multi-topic newsletters
- Timeout thresholds: What constitutes "too long" for each urgency tier

## Non-Goals

- Replace email client for human correspondence
- Commercialization
- Perfect classification (acceptable to miss edge cases)
- Real-time processing (background batch processing acceptable)
