# Segnale: Product Requirements Document

## Vision

An intelligent attention management system that surfaces signals from information noise across multiple sources. Aggregates redundant event coverage into unified entries, presents context-aware feeds based on available attention, and manages dynamic tasks that auto-expire when no longer relevant.

The system works with open protocols and formats - email newsletters, RSS feeds, and federated social media (BlueSky) - while providing an extensible architecture for custom integrations. The initial focus is email, but the design is source-agnostic from the start. All content is processed intelligently: broadcast content is aggregated across sources, verbose messages are summarized for clarity, and transactional content is prioritized based on urgency regardless of source.

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
6. Clean Source Management - Process and archive all content (newsletters, feeds, human correspondence) after extraction. Verbose messages are summarized, broadcast content is aggregated.
7. Meritocratic Signals - Source-agnostic ranking based solely on topic importance to user.
8. Extensible Architecture - Open plugin system for custom source integrations beyond built-in protocols.
9. Inbox Zero Philosophy - Single item at a time, everything must be acted upon. No items left in limbo.

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

All feed items are things to acknowledge, read, or act upon. Items auto-expire when their relevance window closes - week-old world news auto-discards, while high-importance topics persist longer based on learned behaviour.

When content requires action (bills, deliveries, reminders), AI suggests specific tasks that can be created in an external task management system. Segnale handles task creation and maintains awareness of which tasks exist to avoid duplicates or contradictions. The external task manager handles scheduling, reminders, and due dates.

Actionable intelligence means bills get flagged only if unusual (extra charges, significant amount variance), shipping notifications create tasks the day after expected delivery, and delivery PINs trigger immediate notifications.

Task creation flow:
- AI suggests relevant tasks based on content (e.g., "Pay electricity bill by June 15")
- User reviews and approves suggested tasks
- Tasks sent to external task manager via API
- Toast confirmation shown to user
- Feed item remains until user archives/mutes it

Segnale tracks created tasks to enable future features like editing existing tasks or preventing duplicate task suggestions.

### 4. Context-Aware Feed Presentation

Calendar integration infers available attention from scheduled events. Quick breaks show headlines and high-urgency items only. Moderate sessions present a mix of headlines and moderate-depth content. Extended sessions surface deeper content and long-form reads.

The default view is algorithmically ranked by:
1. Urgency tier
2. Topic importance (learned from behaviour)
3. Available attention (inferred from time/calendar)

### 5. Intelligent Content Classification

Different source types require different handling strategies:

Email classification:
- Human email: Sender in address book or personally addressed content → Prioritized highly, verbose messages automatically summarized, archive after processing
- Newsletter: Broadcast distribution, multi-topic digest → Process and archive after signal extraction
- Transactional: Action required (bank, bills, deliveries) → Task creation, notification based on urgency, prioritized regardless of source
- Group email: Multiple recipients → Highlight participants, summarise conversation, fold replies into single feed item
- GitHub notifications: Extensive filtering - only surface items genuinely needing attention

Priority override: Content urgency and importance take precedence over source type. Urgent transactional emails outrank non-urgent personal messages. Address book status increases baseline priority but doesn't guarantee top placement.

RSS feeds: Parse per-item or aggregate full feed based on source type (single-author blog vs. multi-contributor news site)

Social media: Filter by account, hashtag, or algorithmic relevance based on profile preferences

Priority override: Content requiring action (transactional emails, urgent mentions) takes precedence regardless of source type.

### 6. Behaviour Learning & Feedback

Users specify initial interests and preferences per profile. The system then learns from behaviour - tracking which items are read vs dismissed, monitoring time spent on different topics, and observing feedback corrections.

**All user actions contribute to learning**:
- Archive, mute, skip, follow/upvote gestures signal preference
- Time spent on items indicates interest level
- Task creation patterns show action triggers
- Reading depth (summary vs full expansion) reveals attention allocation
- All data stored locally for future product insights and algorithm refinement

Explicit feedback mechanisms:
- Follow/upvote - want more content like this
- Mute - don't show this topic/thread
- Skip/save for later - not now, resurface intelligently

Over time, prioritisation and presentation depth become progressively more accurate, matching user preferences without manual configuration. Behaviour data enables product decisions based on actual usage patterns.

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

### 9. Single-Item Feed Interface

The feed presents one item at a time, eliminating attention competition and enabling focused processing. Each item displays its content with immediately visible actions - no tapping required to see available options.

**Gesture controls**:
- **Swipe right**: Archive - item dealt with, removed from feed, kept for learning
- **Swipe left**: Mute - stop showing updates on this topic/thread (can resurface if becomes critically urgent)
- **Swipe down**: Skip - move to next item, current item resurfaces later based on algorithm
- **Swipe up**: Previous - go back to previous item, undo last action
- **Tap**: Expand/collapse - toggle between summary and detailed view

After archive or mute actions, the next item appears automatically. No manual navigation required for forward progress.

**Visible actions** (shown directly on each item):
- Create task(s) - AI suggests relevant tasks, user approves, sent to external task manager
- Follow/upvote - signal importance, want more like this
- View full thread - for mailing list conversations (when applicable)
- Copy parameter - for OTP codes, tracking numbers (when detected)

**Progress indication**: Display "5 of 23 items" showing position in current feed queue. No detailed queue view to prevent attention fragmentation.

**Empty state**: When all items processed, show completion message with option to view archive.

### 10. Parameter Extraction

Automatically extract actionable parameters from content - OTP codes, delivery PINs, tracking numbers, confirmation codes.

**Extraction flow**:
- Detection runs automatically on all incoming content
- When parameters detected, system notification appears with succinct summary
- Notification shows copy button for each extracted parameter
- Copying parameter to clipboard automatically deletes the source item (soft delete to bin)
- Parameters only accessible while notification is visible
- If multiple parameters detected (e.g., tracking number AND delivery PIN), all are extracted and prioritised in UI

**Example**: Email with "Your delivery PIN is 4782" triggers notification: "Delivery PIN received" with button "Copy: 4782". After copy, email soft-deleted.

This streamlines MFA workflows, package tracking, and other parameter-dependent tasks without manual intervention.

### 11. Thread Evolution Tracking

Mailing list threads and evolving conversations fold into single feed items that update as new replies arrive.

**Thread folding**:
- Multiple emails from same thread = single feed item
- Item shows "5 new replies since last view" indicator
- Tapping item expands to show thread evolution summary
- User's own replies clearly highlighted to track follow-ups needed
- "View full thread" action available for complete history

**Mute behaviour for threads**:
- Muted threads stop generating feed updates
- If thread evolves into materially different or critically important topic, it can resurface with "Previously muted topic now critical" indicator
- Mute applies to specific thread, not entire topic category (unless user specifies otherwise)

This prevents inbox clutter from active discussions while ensuring important developments aren't missed.

## MVP: Email Aggregation

Goal: Daily usability threshold

The first phase focuses exclusively on email as a source, but implements the source-agnostic architecture from the start to enable future expansion.

Scope:
- Multi-source event aggregation (email newsletters)
- Email classification (human, newsletter, transactional)
- Human email summarisation for verbose messages
- Single-item feed interface with gesture controls
- Parameter extraction (OTP codes, delivery PINs)
- Thread evolution tracking for mailing lists
- Basic task creation integration (external task manager)
- Email management (archive, mute, process all email types)
- In-app email viewing
- Behaviour tracking and learning foundation

Success criteria:
- Single aggregated entry for redundant events
- Reduced time spent consuming newsletter content by >50%
- Zero missed OTP codes or delivery PINs
- Daily usage begins with inbox zero workflow

## Out of Scope

- Work emails and corporate email systems
- Commercialisation or multi-user support
- Real-time processing (background batch processing acceptable)
- Perfect classification (acceptable to miss edge cases)

## Future Scope (Not MVP)

- Sending and replying to emails - desired eventually, but MVP focuses on consumption only
- AI agent for security vulnerability analysis across GitHub repositories
- Search functionality for archived items
- Multi-source aggregation (RSS, social media) - Phase 2+

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
