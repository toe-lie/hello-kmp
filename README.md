# Testing apprenticeship for an experienced mobile developer

Build a small app to practise making changes with evidence. Product complexity is deliberately limited: the learning target is choosing useful tests, discovering design through feedback, and shipping repeatably.

The workspace contains list/detail navigation, empty-state rendering, and session-only bookmarks. The developer has reported passing local/CI checks, failure-detection exercises, and installation of a CI debug APK through M2. These are historical reports, not fresh verification of this documentation change. See [local commands](docs/07-commands.md) and the [API scope decision](docs/sessions/2026-09-29-api-scope.md).

## Start here

1. Read the [project brief and milestones](docs/01-project.md).
2. Use the [development loop](docs/02-development-loop.md) during each coding session.
3. Consult the [test strategy](docs/03-test-strategy.md) when deciding where a test belongs.
4. Build the [delivery pipeline](docs/04-delivery.md) alongside the first feature.
5. Copy the [session and review templates](docs/05-practice.md) to record evidence.

[AGENTS.md](AGENTS.md) defines how coding agents should collaborate with you. [Sources](docs/06-sources.md) distinguishes verified references from this project's own decisions.

## Working assumptions

The exercise remains a two-screen **News List and Detail** app. Next, integrate article list and detail APIs, then backend-owned bookmarks. A backend and wider account system are assumed; registration/sign-in implementation is excluded. No real API contract or service has been verified yet. Local persistence and offline synchronization are deferred.

The current implementation uses KMP and shared Compose UI, with Android-first testing. The original stack suggestion is now reflected in code; changes remain deliberate decisions rather than new requirements inferred from the workspace name.

Default agent mode is coaching: you write the important tests and implementation. Explicitly delegate a slice when you want the agent to implement it. No need to finish the suggested reading before starting.

Next, agree the proposed article-read contract in [M3](docs/01-project.md), then select one list-success example. Preserve the working M0–M2 baseline; later milestones are a backlog, not permission to implement everything at once.
