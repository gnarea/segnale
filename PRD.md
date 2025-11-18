# Product Requirements Document: Segnale

## Vision

An intelligent attention management system that surfaces signals from information noise across multiple sources. Aggregates redundant event coverage into unified entries, presents context-aware feeds based on available attention, and manages dynamic tasks that auto-expire when no longer relevant.

The system works with open protocols and formats - email newsletters, RSS feeds, and federated social media (BlueSky) - while providing an extensible architecture for custom integrations. The initial focus is email newsletters, but the design is source-agnostic from the start. Human correspondence remains untouched; only broadcast content (newsletters, feeds, social posts) is processed for signal extraction.

## Problem Statement

The same event gets covered across multiple sources - newsletters, RSS feeds, social media - creating redundant noise consumed at different times throughout the week. Major announcements (AI lab releases, world events) appear in multiple digest-style formats, forcing context-switching between sources to understand a single topic.

Information consumption happens in fragmented windows with varying levels of attention available. Mobile notifications get dismissed, feeds pile up, and valuable signals are missed or found too late.

Current systems don't distinguish between a 2-minute break (headlines only) and a 30-minute focused session (deep dives). All content is presented equally regardless of available cognitive bandwidth.

## Core Principles

1. Aggregation First - Single entry per event across all sources. Never show the same news multiple times.
2. Attention-Aware - Present information depth matched to available time and context.
3. Profile Segregation - Distinct interest areas (Technology, Motorcycles, World Politics) consumed independently.
4. Dynamic Tasks - Tasks auto-expire when relevance window closes. No ever-growing to-do lists.
5. Behaviour Learning - AI-driven prioritisation with explicit feedback mechanisms for urgency, depth, and relevance.
6. Clean Source Management - Process and archive broadcast content (newsletters, feeds) after extraction. Human correspondence stays untouched.
7. Meritocratic Signals - Source-agnostic ranking based solely on topic importance to user.
8. Extensible Architecture - Open plugin system for custom source integrations beyond built-in protocols.

## User Scenarios

### Scenario 1: Two-Minute Break
- **Context**: Minimal attention available
- **Need**: Headlines only for FYI topics (world news, hobby content)
- **Behaviour**: Quick scan, mark as read, move on

### Scenario 2: Lunch Break (30-60 minutes, once/week)
- **Context**: Dedicated focus time
- **Need**: Deep dives into high-priority topics (latest AI features, cybersecurity developments)
- **Behaviour**: Executive summary → deep dive → access original sources if needed

### Scenario 3: Morning Breakfast
- **Context**: Relaxed, moderate attention available
- **Need**: Mix of headlines and moderate-depth content
- **Behaviour**: Scan feed, engage with interesting items, defer others for later

### Scenario 4: Evening Commute
- **Context**: Extended free time, willing to dive deep
- **Need**: Long-form content on topics of high interest
- **Behaviour**: Read aggregated analyses, explore multiple source perspectives

## Core Features

### 1. Multi-Source Event Aggregation

The same AI model release might appear in 5 different newsletters, 3 RSS feeds, and multiple social media posts - all with overlapping information consumed at different times. Segnale extracts events from each source, identifies the same event across different channels, and creates a unified feed entry.

Each aggregated entry contains:
- Event title and executive summary
- Source count and names (e.g., "Covered by 5 sources: Newsletter A, RSS Feed B, BlueSky Post C...")
- Deep dive section (length varies by topic importance to user)
- Links to original content (viewable in-app)

Example: "Anthropic releases Claude 4" covered by AI Daily newsletter, Hacker News RSS, and multiple BlueSky posts → Single entry titled "Anthropic Claude 4 Release" with combined summary from all perspectives.

### 2. Profile-Based Interest Segregation

Different interest areas are consumed independently through distinct profiles:
- Technology: AI, cybersecurity, software engineering, and their intersection
- Motorcycles: Off-road routes, gear reviews, community discussions
- World Politics: International news and events

Each profile has its own set of sources - newsletters, RSS feeds, social media accounts - and content never bleeds across profiles. Switch profiles based on mood and context.

### 3. Dynamic Task Management

All feed items are tasks - things to acknowledge, read, or act upon. Tasks are automatically created from content requiring action (bills, deliveries, group emails) and expire when their relevance window closes. Week-old world news auto-discards, while high-importance topics persist longer.

Actionable intelligence means bills get flagged only if unusual (extra charges, significant amount variance), shipping notifications create tasks the day after expected delivery, and delivery PINs trigger immediate notifications.

Available actions:
- Mark as read (item disappears from feed)
- Save for later (re-surfaces based on priority)
- Create manual task
- Provide feedback (adjust urgency, depth, relevance)

### 4. Context-Aware Feed Presentation

Calendar integration infers available attention from scheduled events. Quick breaks show headlines and high-urgency items only. Moderate sessions present a mix of headlines and moderate-depth content. Extended sessions surface deeper content and long-form reads.

The default view is algorithmically ranked by:
1. Urgency tier
2. Topic importance (learned from behaviour)
3. Available attention (inferred from time/calendar)

### 5. Intelligent Content Classification

Different source types require different handling strategies:

Email classification:
- Human email: Sender in address book or personally addressed content → Archive after processing
- Newsletter: Broadcast distribution, multi-topic digest → Process and archive after signal extraction
- Transactional: Action required (bank, bills, deliveries) → Task creation, notification based on urgency
- Group email: Multiple recipients → Highlight participants, summarise conversation
- GitHub notifications: Extensive filtering - only surface items genuinely needing attention

RSS feeds: Parse per-item or aggregate full feed based on source type (single-author blog vs. multi-contributor news site)

Social media: Filter by account, hashtag, or algorithmic relevance based on profile preferences

Priority override: Content requiring action (transactional emails, urgent mentions) takes precedence regardless of source type.

### 6. Behaviour Learning & Feedback

Users specify initial interests and preferences per profile. The system then learns from behaviour - tracking which items are read vs dismissed, monitoring time spent on different topics, and observing feedback corrections.

Explicit feedback mechanisms:
- Adjust urgency for specific items
- Request more/less depth on topics
- Flag false positives (shouldn't have been surfaced)

Over time, prioritisation and presentation depth become progressively more accurate, matching user preferences without manual configuration.

### 7. Urgency Tiers

Immediate (Push Notification):
- Delivery requiring PIN upon receipt
- Critical transactional content (learned through behaviour)

Hours (Notification with Timeout):
- To be defined through usage patterns

Days/Weeks (Passive Until Opened):
- AI lab releases
- Software engineering newsletters
- Non-urgent transactional content

FYI Only (No Notification):
- World news
- Hobby content (motorcycles, discussion threads)

Urgency is determined by AI learning from behaviour, user feedback, and explicit preferences.

### 8. Extensible Source Architecture

While the core system ships with built-in support for open protocols (email, RSS, BlueSky), a plugin API allows custom source integrations without modifying the core codebase.

Plugin architecture:
- Standardised interface for content fetching and parsing
- Event extraction contract (title, summary, timestamp, source metadata)
- Profile association and filtering hooks
- Custom urgency and classification logic

Example use cases:
- Slack channel monitoring with keyword filtering
- Custom corporate tools integration
- Niche social platforms or forums
- Proprietary newsletter formats requiring special parsing

Plugins are first-class citizens in the aggregation pipeline - custom source events are deduplicated alongside built-in sources, participate in cross-source event matching, and follow the same urgency and presentation rules.

## Progressive Delivery Strategy

### Phase 1: Email Aggregation (Minimum Viable)
Goal: Daily usability threshold

The first phase focuses exclusively on email as a source, but implements the source-agnostic architecture from the start to enable future expansion.

Scope:
- Multi-source event aggregation (email newsletters)
- Email classification (human, newsletter, transactional)
- Basic feed presentation (most urgent first)
- Email management (archive human, process and archive newsletters)
- In-app email viewing
- Plugin API foundation (even if no additional sources yet)

Success criteria: Single aggregated entry for redundant events, reduced time spent consuming newsletter content by >50%, daily usage begins.

### Phase 2: Task & Urgency Management
Goal: Replace email client for all personal correspondence

Scope:
- Dynamic task creation and auto-expiry
- Multi-tier urgency classification
- Push notifications for immediate items
- Timeout-based notifications
- Transactional email intelligence (bill anomalies, shipping tracking)

Success criteria: Zero manual email triage, all actionable items surfaced at appropriate times, Gmail only opened for sending replies.

### Phase 3: Context-Aware Presentation
Goal: Attention-optimised information consumption

Scope:
- Calendar integration for context inference
- Profile segregation (Technology, Motorcycles, World Politics)
- Adaptive depth presentation (headlines vs deep dives)
- Behaviour learning and feedback loops

Success criteria: Context-appropriate content presented without manual profile switching >80% of the time, user adjusts urgency/depth <10% of items.

### Phase 4: Multi-Source Aggregation
Goal: Unified information hub across all sources

Scope:
- RSS feed integration (blogs, news sites, podcasts)
- Social media integration (Reddit, HackerNews, YouTube, BlueSky)
- Cross-platform event aggregation
- Meritocratic ranking across all sources

Success criteria: All information consumption happens through Segnale, email client only used for sending messages.

## Success Criteria

Phase 1:
- Redundant event coverage reduced to single entries
- Time spent consuming newsletters reduced by >50%
- Daily usage begins

Phase 2:
- Zero missed actionable items (bills, deliveries, important content)
- Email client only opened for sending replies

Phase 3:
- Context-appropriate content presented without manual profile switching >80% of the time
- User adjusts urgency/depth <10% of items (high accuracy)

Phase 4:
- All information sources consumed via Segnale
- Email client fully replaced for consumption

## Out of Scope

- Work emails and corporate email systems
- Commercialisation or multi-user support
- Real-time processing (background batch processing acceptable)
- Perfect classification (acceptable to miss edge cases)
- Sending/replying to emails (use existing email client)

## Open Questions

### Event Matching
- Algorithm for determining "same event" across sources: semantic similarity vs keyword matching vs embedding-based clustering?
- Handling multi-topic sources: parse as single entity or extract distinct events per item?
- Cross-source matching reliability: how to handle slight title variations or different perspectives on the same event?

### Task Expiry
- Default expiry windows per urgency tier (e.g., FYI world news = 7 days, AI releases = 30 days)?
- User-configurable or learned from behaviour?

### Behaviour Learning
- Cold-start problem: how much manual configuration needed before AI takes over?
- Feedback collection: explicit prompts vs passive observation?

### Source Management
- Email: Reliability of Gmail API for processing and archiving. Fallback if operations fail?
- RSS: Polling frequency vs. resource usage. How to handle high-volume feeds?
- Social media: API access vs scraping for platforms without official APIs. Rate limiting strategies?

### Plugin Architecture
- Plugin API surface: what level of access to core aggregation pipeline?
- Security model: sandboxing third-party plugins vs trusting user-installed code?
- Plugin discovery: centralized registry or manual installation only?

### Calendar Integration
- Privacy concerns: what calendar data accessed? Local processing only?
- Accuracy: how reliably can available attention be inferred from calendar events?
