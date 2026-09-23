# Testing apprenticeship for an experienced mobile developer

Build a small app to practise making changes with evidence. Product complexity is deliberately limited: the learning target is choosing useful tests, discovering design through feedback, and shipping repeatably.

The workspace contains a KMP/Compose template and an initial News List showing two bundled titles. The developer has reported a passing Android device UI test. M0 is still in progress: CI evidence and clean-checkout reproducibility are not yet established. See [local commands](docs/07-commands.md) and [the first session record](docs/sessions/2026-09-23-news-list.md).

## Start here

1. Read the [project brief and milestones](docs/01-project.md).
2. Use the [development loop](docs/02-development-loop.md) during each coding session.
3. Consult the [test strategy](docs/03-test-strategy.md) when deciding where a test belongs.
4. Build the [delivery pipeline](docs/04-delivery.md) alongside the first feature.
5. Copy the [session and review templates](docs/05-practice.md) to record evidence.

[AGENTS.md](AGENTS.md) defines how coding agents should collaborate with you. [Sources](docs/06-sources.md) distinguishes verified references from this project's own decisions.

## Working assumptions

The exercise is a simple **News List and Detail** app with exactly two screens, bundled offline articles, and a bookmark toggle. Navigation and bookmarking are the product scope; local bookmark persistence follows as a testing exercise. Kotlin Multiplatform (KMP), Android first and iOS second, is a suggested stack because this workspace is named KMP. It is not a user-confirmed requirement. Use a familiar stack if learning KMP would compete with learning testing. The behavior examples and workflow still apply.

Default agent mode is coaching: you write the important tests and implementation. Explicitly delegate a slice when you want the agent to implement it. No need to finish the suggested reading before starting.

For the first session, take milestone M0 only. End with one automated test that demonstrably fails when the app's expected behavior is broken, a passing build, and a reproducible way to run both. Later milestones are a backlog, not permission to build everything at once.
