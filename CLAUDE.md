# CLAUDE.md

Segnale is a local-first AI signal extraction system that aggregates redundant event coverage from newsletters. Built with Kotlin Multiplatform (Android, iOS, Desktop) and Compose Multiplatform.

## Tech Stack

- Kotlin Multiplatform for shared business logic.
- Compose Multiplatform for declarative UI.
- Room KMP for local database.
- Gmail API for email access (NOT IMAP).
- Anthropic Java SDK with custom LLM abstraction layer.
- Koin for dependency injection.
- kotlinx.coroutines for async/reactive programming.
- WorkManager for background sync (Android).

## Architecture

**Clean Architecture with dependency rule**: Domain (commonMain) → Data (platform-specific) → Presentation

- Dependencies ALWAYS point inward.
- Domain layer has zero platform dependencies.

## Module Organization

- `composeApp/commonMain/`: Platform-agnostic domain logic and UI.
- `composeApp/androidMain/`: Gmail API, Room driver, WorkManager.
- `composeApp/iosMain/`: iOS-specific implementations.
- `composeApp/jvmMain/`: Desktop JVM implementations.
- `iosApp/`: iOS application wrapper (Xcode/Swift).

## Code Style

- No wildcard imports - use explicit imports only.
- Zero-tolerance detekt violations (`maxIssues: 0`, `warningsAsErrors: true`).
- 120 character maximum line length.
- Explicit return types required on all functions (including `: Unit`).
- Composable functions use PascalCase (e.g., `App()`, `MyScreen()`).
- Constants in SCREAMING_SNAKE_CASE.
- Data classes must be immutable with no functions.
- No unused imports or parameters.

## Testing Constraints

**Critical**: MockK only supports JVM/Android, NOT iOS/Native.

- Use `jvmTest` or `androidInstrumentedTest` for MockK-dependent tests.
- Keep `commonTest` MockK-free for cross-platform compatibility.

## Dependency Management

We use Gradle lock files. When adding, updating or removing dependencies, regenerate lock files:

```bash
./gradlew dependencies --write-locks  # Update lock files
```

## Essential Commands

```bash
./gradlew allTests          # All platform tests
./gradlew jvmTest           # JVM tests only
./gradlew detekt            # Static analysis (zero-tolerance)
./gradlew check             # All verification checks
./gradlew lintFix           # Lint with auto-fixes
./gradlew :composeApp:assembleDebug  # Android APK
./gradlew :composeApp:run   # Desktop JVM
```

## Security

- Store API keys in platform-specific secure storage (Android Keystore, iOS Keychain).
- Local-first: Only newsletter content sent to LLM APIs, user data stays local.

## Key Decisions

- **Gmail API over IMAP**: Google-recommended approach for reliability.
- **LLM abstraction**: Provider-agnostic interface enables vendor switching.
- **Background sync**: WorkManager for guaranteed, system-optimized scheduling.

## Documentation

- Architecture details: `arch.md`.
- Product vision & roadmap: `PRD.md`.
- Setup & build: `README.md`.
