# Product Requirements Document: Segnale

## Vision

An intelligent attention management system that replaces traditional email clients by surfacing signals from information noise. Aggregates redundant event coverage across sources into unified entries, presents context-aware feeds based on available attention, and manages dynamic tasks that auto-expire when no longer relevant.

**Not an email client replacement**—augments email by extracting signal whilst leaving human correspondence untouched. Long-term goal: comprehensive information hub aggregating newsletters, social media, and video content.

## Problem Statement

**Current pain**: Same event covered across multiple newsletters creates redundant noise consumed at different times throughout the week. Major announcements (AI lab releases, world events) buried in digest-style newsletters force context-switching between sources to understand a single topic.

**Workflow inefficiency**: Limited windows (3x/week on computer, occasional lunch breaks) mean information consumption is fragmented. Mobile notifications dismissed, emails pile up, valuable signals missed or found too late.

**Attention mismatch**: No distinction between 2-minute toilet break (headlines only) and 30-minute commute (deep dives). All content presented equally regardless of available cognitive bandwidth.

## Core Principles

1. **Aggregation First**: Single entry per event across all sources. Never show the same news multiple times.
2. **Attention-Aware**: Present information depth matched to available time and context.
3. **Profile Segregation**: Distinct interest areas (Technology, Motorcycles, World Politics) consumed independently.
4. **Dynamic Tasks**: Tasks auto-expire when relevance window closes. No ever-growing to-do lists.
5. **Behaviour Learning**: AI-driven prioritisation with explicit feedback mechanisms for urgency, depth, and relevance.
6. **Email Replacement**: Archive human emails, delete newsletters post-processing. Never leave unread or labelled.
7. **Meritocratic Signals**: Source-agnostic ranking based solely on topic importance to user.

## User Scenarios

### Scenario 1: Two-Minute Toilet Break
- **Context**: Working hours, minimal attention available
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

**Problem Solved**: Single AI model release covered by 5 newsletters → 5 separate emails with overlapping information.

**Solution**: Extract events from each newsletter, identify same event across sources, create unified feed entry.

**Aggregated Entry Contents**:
- Event title and executive summary
- Source count and names (e.g., "Covered by 5 sources: Newsletter A, Newsletter B...")
- Deep dive section (length varies by topic importance to user)
- Links to original emails (viewable in-app)

**Example**: "Anthropic releases Claude 4" covered by AI Daily, Software Weekly, and HN Digest → Single entry titled "Anthropic Claude 4 Release" with combined summary from all three perspectives.

### 2. Profile-Based Interest Segregation

**Profiles**:
- **Technology**: AI, cybersecurity, software engineering, and their intersection
- **Motorcycles**: Off-road routes, newsletters, Reddit communities
- **World Politics**: International news and events

**Behaviour**:
- Each profile consumes content independently
- Switch profiles based on mood/context
- Profile-specific sources (newsletters, social media accounts, subreddits)

### 3. Dynamic Task Management

**Mental Model**: All feed items are tasks—things to acknowledge, read, or act upon.

**Task Properties**:
- **Auto-Creation**: Extracted from emails requiring action (bills, deliveries, group emails)
- **Auto-Expiry**: Tasks expire when relevance window closes (e.g., week-old world news auto-discarded)
- **Actionable Intelligence**: Bills flagged only if unusual (extra charges, significant amount variance)

**Examples**:
- Shipping notification → Task created day *after* expected delivery: "Check if package arrived"
- Delivery requiring PIN → Immediate notification with task
- Newsletter event → FYI task that expires after configurable period

**Task Actions**:
- Mark as read (item disappears from feed)
- Save for later (re-surfaces based on priority)
- Create manual task
- Provide feedback (adjust urgency, depth, relevance)

### 4. Context-Aware Feed Presentation

**Calendar Integration**: Infer available attention from calendar events.

**Presentation Logic**:
- **Working hours**: Short-form, headlines only, high-urgency items
- **Lunch break**: Mix of headlines and moderate-depth content
- **Breakfast/evening**: Deeper content, long-form reads

**Default View**: Algorithmically ranked by:
1. Urgency tier
2. Topic importance (learned from behaviour)
3. Available attention (inferred from time/calendar)

### 5. Intelligent Email Classification

**Heuristics**:
- **Human email**: Sender in address book OR content personally addressed → Archive after processing
- **Newsletter**: Broadcast distribution, multi-topic digest → Delete after signal extraction
- **Transactional**: Action required (bank, bills, deliveries) → Task creation, notification based on urgency
- **Group email**: Multiple recipients → Highlight participants, summarise conversation
- **GitHub notifications**: Extensive filtering—only surface items genuinely needing attention

**Priority Override**: Emails requiring action (banks, deliveries) prioritised regardless of sender type.

### 6. Behaviour Learning & Feedback

**Initial Configuration**: User specifies interests and preferences per profile.

**Learning Mechanisms**:
- Track which items are read vs dismissed
- Monitor time spent on different topics
- Observe feedback corrections (urgency adjustments, depth preferences)

**Explicit Feedback**:
- Adjust urgency for specific items
- Request more/less depth on topics
- Flag false positives (shouldn't have been surfaced)

**Outcome**: Progressively accurate prioritisation and presentation depth matching user preferences.

### 7. Urgency Tiers

**Immediate (Push Notification)**:
- Delivery requiring PIN upon receipt
- Critical transactional emails (learned behaviour)

**Hours (Notification with Timeout)**:
- *(To be defined through usage patterns)*

**Days/Weeks (Passive Until Opened)**:
- AI lab releases
- Software engineering newsletters
- Non-urgent transactional emails

**FYI Only (No Notification)**:
- World news
- Hobby content (motorcycles, Reddit threads)

**Determination**: AI-driven based on learned behaviour, user feedback, and explicit preferences.

## Progressive Delivery Strategy

### Phase 1: Email Aggregation (Minimum Viable)
**Goal**: Daily usability threshold.

**Scope**:
- Multi-source event aggregation (newsletters only)
- Email classification (human, newsletter, transactional)
- Basic feed presentation (most urgent first)
- Email management (archive human, delete newsletters)
- In-app email viewing

**Success**: Single aggregated entry for redundant events, reduced time spent consuming newsletter content.

### Phase 2: Task & Urgency Management
**Goal**: Replace email client for all personal correspondence.

**Scope**:
- Dynamic task creation and auto-expiry
- Multi-tier urgency classification
- Push notifications for immediate items
- Timeout-based notifications
- Transactional email intelligence (bill anomalies, shipping tracking)

**Success**: Zero manual email triage, all actionable items surfaced at appropriate times.

### Phase 3: Context-Aware Presentation
**Goal**: Attention-optimised information consumption.

**Scope**:
- Calendar integration for context inference
- Profile segregation (Technology, Motorcycles, World Politics)
- Adaptive depth presentation (headlines vs deep dives)
- Behaviour learning and feedback loops

**Success**: Feed presentation matches available attention without manual configuration.

### Phase 4: Multi-Source Aggregation
**Goal**: Unified information hub across all sources.

**Scope**:
- Social media integration (Twitter, Reddit, HackerNews, YouTube, BlueSky)
- Cross-platform event aggregation
- Meritocratic ranking across sources

**Success**: Email client fully replaced, all information consumption via Segnale.

## Success Criteria

**Phase 1**:
- Redundant event coverage reduced to single entries
- Time spent consuming newsletters reduced by >50%
- Daily usage begins

**Phase 2**:
- Zero missed actionable items (bills, deliveries, important emails)
- Gmail only opened for sending replies

**Phase 3**:
- Context-appropriate content presented without manual profile switching >80% of the time
- User adjusts urgency/depth <10% of items (high accuracy)

**Phase 4**:
- Gmail abandoned for self-hosted/paid SMTP
- All information sources consumed via Segnale

## Out of Scope

- Work emails and corporate email systems
- Commercialisation or multi-user support
- Real-time processing (background batch processing acceptable)
- Perfect classification (acceptable to miss edge cases)
- Sending/replying to emails (use existing email client)

## Open Questions

### Event Matching
- Algorithm for determining "same event" across sources: semantic similarity vs keyword matching vs embedding-based clustering?
- Handling multi-topic newsletters: parse as single entity or extract distinct events per email?

### Task Expiry
- Default expiry windows per urgency tier (e.g., FYI world news = 7 days, AI releases = 30 days)?
- User-configurable or learned from behaviour?

### Behaviour Learning
- Cold-start problem: how much manual configuration needed before AI takes over?
- Feedback collection: explicit prompts vs passive observation?

### Email Management
- Gmail deletion reliability: ensure processed emails deleted without manual intervention
- Fallback if deletion fails: archive with special label?

### Calendar Integration
- Privacy concerns: what calendar data accessed? Local processing only?
- Accuracy: how reliably can available attention be inferred from calendar events?

### Social Media Integration (Phase 4)
- API access vs scraping for platforms without official APIs
- Rate limiting and data freshness strategies
