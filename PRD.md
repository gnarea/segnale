# Segnale: Product Requirements Document

## Overview

Segnale is an intelligent attention management system that aggregates information from multiple sources into a unified feed. It eliminates redundant coverage of the same events across newsletters, RSS feeds, and social media, presenting each topic once with combined insights from all sources. The system matches content depth to available attention, adapts to learned preferences, and manages tasks that auto-expire when no longer relevant.

Today, major events appear across dozens of sources throughout the week - the same AI release covered in five newsletters, three RSS feeds, and countless social posts. Each requires separate attention at different times, forcing context-switching to piece together a complete picture. Meanwhile, information consumption happens in fragmented windows: a 2-minute break between meetings, a 30-minute lunch session, an evening commute. Current systems ignore this reality, presenting everything with equal urgency and depth regardless of available cognitive bandwidth. Valuable signals are buried in noise, urgent items arrive without priority, and feeds pile up until abandoned.

Segnale works with open protocols (email, RSS, BlueSky) and provides an extensible plugin architecture for custom integrations. The initial focus is email, but the design is source-agnostic from the start. Content is processed intelligently: broadcast content aggregated across sources, verbose messages summarised, transactional content prioritised by urgency regardless of origin.

## Core Principles

1. Aggregation First - Single entry per event across all sources. Never show the same news multiple times.
2. Attention-Aware - Present information depth matched to available time and context.
3. Profile Segregation - Distinct interest areas (Technology, Motorcycles, World Politics) consumed independently.
4. Dynamic Tasks - Tasks auto-expire when relevance window closes. No ever-growing to-do lists.
5. Behaviour Learning - AI-driven prioritisation with explicit feedback mechanisms for urgency, depth, and relevance.
6. Clean Source Management - Process and archive all content (newsletters, feeds, human correspondence) after extraction. Verbose messages are summarised, broadcast content is aggregated.
7. Meritocratic Signals - Source-agnostic ranking based solely on topic importance to user.
8. Extensible Architecture - Open plugin system for custom source integrations beyond built-in protocols.
9. Inbox Zero Philosophy - Single item at a time, everything must be acted upon. No items left in limbo.

## User Scenarios

| Context | Attention Level | Content Depth | Example |
|---------|----------------|---------------|---------|
| Two-minute break | Minimal | Headlines only | Quick scan between meetings |
| Morning breakfast | Moderate | Mixed depth | Scan and engage with interesting items |
| Weekly lunch session | High | Deep dives | 30-60 minutes focused reading |
| Evening commute | Extended | Long-form | Aggregated analyses from multiple sources |

## Core Features

### 1. Multi-Source Event Aggregation

Events are extracted from each source, matched across channels, and unified into single feed entries. Never show the same news multiple times.

Each aggregated entry contains:
- Event title and executive summary.
- Source count and names.
- Deep dive section (length varies by topic importance to user).
- Links to original content (viewable in-app).

### 2. Profile-Based Interest Segregation

Users create custom profiles for distinct interest areas (e.g., technology, hobbies, world affairs). Each profile maintains:

- Independent source subscriptions (newsletters, RSS feeds, social accounts).
- Isolated content streams (no cross-profile bleeding).
- Profile-specific behaviour learning and preferences.

Switch profiles based on mood and context.

### 3. Dynamic Task Management

Feed items auto-expire when their relevance window closes. Week-old news auto-discards, while high-importance topics persist longer based on learned behaviour.

When content requires action (bills, deliveries, reminders), AI suggests tasks for external task management systems. User reviews and approves, then tasks are sent via API. Segnale tracks created tasks to prevent duplicates.

Actionable intelligence: bills flagged only if unusual, shipping notifications create tasks after expected delivery, delivery PINs trigger immediate notifications.

### 4. Context-Aware Feed Presentation

Calendar integration infers available attention from scheduled events. Quick breaks show headlines and high-urgency items only. Moderate sessions present a mix of headlines and moderate-depth content. Extended sessions surface deeper content and long-form reads.

The default view is algorithmically ranked by:

1. Urgency tier.
2. Topic importance (learned from behaviour).
3. Available attention (inferred from time/calendar).

### 5. Intelligent Content Classification

Email classification:

- Human email: Prioritised highly, verbose messages auto-summarised, archive after processing.
- Newsletter: Process and archive after signal extraction.
- Transactional: Task creation, notification based on urgency.
- Group email: Highlight participants, fold replies into single item.
- GitHub notifications: Filter to surface only items needing attention.

RSS feeds: Parse per-item or aggregate full feed based on source type.

Social media: Filter by account, hashtag, or algorithmic relevance.

Priority override: Content urgency and importance take precedence over source type. Urgent transactional content outranks non-urgent personal messages.

### 6. Behaviour Learning & Feedback

Users specify initial interests per profile. The system learns from behaviour: which items are read vs dismissed, time spent on topics, and feedback corrections.

All user actions contribute to learning:

- Archive, mute, skip, follow/upvote gestures signal preference.
- Time spent and reading depth reveal attention allocation.
- Task creation patterns show action triggers.
- All data stored locally for algorithm refinement and product insights.

Over time, prioritisation and presentation depth become progressively more accurate without manual configuration.

### 7. Urgency Tiers

- **Immediate (Push Notification)**: Delivery PINs, critical transactional content.
- **Hours (Notification with Timeout)**: To be defined through usage patterns.
- **Days/Weeks (Passive Until Opened)**: AI releases, newsletters, non-urgent transactional content.
- **FYI Only (No Notification)**: World news, hobby content.

Urgency determined by AI learning from behaviour, user feedback, and explicit preferences.

### 8. Extensible Source Architecture

Built-in support for open protocols (email, RSS, BlueSky). Plugin API allows custom source integrations without modifying core codebase.

Plugins are first-class citizens in the aggregation pipeline - custom source events are deduplicated alongside built-in sources, participate in cross-source event matching, and follow the same urgency and presentation rules.

### 9. Single-Item Feed Interface

One item displayed at a time for focused processing. Actions immediately visible, no tapping required.

**Gestures:**

- Swipe right: Archive.
- Swipe left: Mute topic/thread (can resurface if critically urgent).
- Swipe down: Skip (resurfaces later algorithmically).
- Swipe up: Previous (undo last action).
- Tap: Expand/collapse summary ↔ detail.

**Visible actions:**

- Create task(s): AI suggests, user approves, sent to external task manager.
- Follow/upvote: Signal importance.
- View full thread: For mailing list conversations.
- Copy parameter: For OTP codes, tracking numbers.

**Progress:** "5 of 23 items" position indicator.

**Empty state:** Completion message with archive access option.

### 10. Parameter Extraction

Automatically extract actionable parameters: OTP codes, delivery PINs, tracking numbers, confirmation codes.

**Behaviour:**

- Detection runs automatically on all incoming content.
- System notification appears with copy button per parameter.
- Copying parameter auto-deletes source item (soft delete).
- Multiple parameters: all extracted and prioritised in UI.

Streamlines MFA workflows, package tracking, and parameter-dependent tasks.

### 11. Thread Evolution Tracking

Mailing list threads fold into single feed items that update as new replies arrive.

**Thread folding:**

- Multiple emails from same thread = single feed item.
- "5 new replies since last view" indicator.
- Tap to expand thread evolution summary.
- User's own replies highlighted.
- "View full thread" action for complete history.

**Mute behaviour:**

- Muted threads stop generating updates.
- Can resurface if becomes critically important ("Previously muted topic now critical").
- Mute applies to specific thread, not entire topic category.

## Out of Scope

- Work emails and corporate email systems.
- Commercialisation or multi-user support.
- Real-time processing (background batch processing acceptable).
