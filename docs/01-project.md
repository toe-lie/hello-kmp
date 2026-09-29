# Project brief and milestones

## News List and Detail

A mobile training app with exactly two screens: **News List** and **News Detail**, with bookmarking. Practise selecting useful tests, integrating external systems, and delivering repeatably.

Decision updated on 2026-09-29: assume a backend and a wider product with registration and sign-in. Integrate article list and detail APIs first, then backend-owned bookmarks. Authentication screens, registration, token acquisition/refresh, backend implementation, offline synchronization, and a local database are outside the current exercise. Search, pagination, sharing, notifications, and extra screens remain out of scope.

The existing implementation uses bundled articles and session-only bookmarks. Preserve its tests and behavior while introducing the API boundary incrementally. In-memory bookmark state is not durable and must not be described as surviving Activity recreation or process death.

### Screens and behavior

1. **News List:** show article titles in source order and bookmark indicators. Select an article by stable ID to open its detail.
2. **News Detail:** show the selected article's title and body, bookmark action, and back navigation to the list.

Loading, empty, error, retry, and save-in-progress states belong inside these screens. No separate bookmarks or authentication screen is added.

- Article identity is independent of the title. Selecting a second article must not display stale content from the first.
- A successful empty list response shows “No news available”. Loading and failed requests must not masquerade as an empty result.
- Failed reads show an error and an explicit retry action. Agree how existing content behaves during retry before implementing that example.
- Bundled data remains useful as deterministic test fixtures; do not silently fall back to it on a real API failure.
- M2 bookmarks are session-only, initially unbookmarked, and independent per article. Add/remove changes appear in Detail and List without reordering articles or changing their content.
- At M5, the backend is authoritative for the current user's bookmarks. Unknown/loading bookmark status is distinct from unbookmarked. Screens use app-facing state and actions, not API response shapes.
- Proposed M5 write policy: show a pending state and claim success only after server confirmation. A failed request retains the last confirmed state and offers retry. Agree timeout reconciliation and safe retry semantics with the backend before implementing writes; a lost response can leave the server outcome unknown.
- Relaunch at M5 loads server state for the same controlled user; this proves backend integration, not local database durability. Offline bookmark writes are deferred.

## Contract before implementation

A real service URL, provider, authentication requirements, and complete schema have not been supplied. Do not claim an existing backend contract has been verified. The following is a **proposed practice contract**, to agree or replace before M3 implementation:

| Operation | Proposed successful response |
| --- | --- |
| `GET /articles` | HTTP 200 JSON array of `{ id, title }` |
| `GET /articles/{id}` | HTTP 200 JSON object `{ id, title, body }` |

The proposed detail request teaches a separate read boundary. If the actual list already includes the body, reconsider whether another request adds value. Resolve ID types, required fields, response envelopes, ordering, error/status mapping, detail-not-found behavior, timeouts, and authentication before coding the corresponding examples. Do not invent bookmark endpoint paths or mutation semantics yet.

Prefer public article reads for this exercise if the contract permits them. If reads require authentication, provide a controlled test-session boundary and an authorised test environment; do not bypass server authentication or hard-code privileged credentials. Mock-server credentials may be synthetic and non-secret. A real endpoint smoke check requires authorised configuration.

## Stack and design

The workspace currently uses Kotlin Multiplatform, shared Compose UI, Navigation 3, and Android-first verification. iOS scaffolding is not proof of iOS behavior. Keep compatible tool versions in the project declarations; choose an HTTP client only after the contract and supported-target needs are clear.

Introduce a small app-owned article-loading boundary when the real HTTP dependency requires it. Translate transport responses into app-facing data there. Do not build speculative repositories or a local database. A short, honestly labelled spike is appropriate for unfamiliar HTTP or coroutine APIs. Preserve important behavior tests across implementation changes.

## Milestones with exit evidence

M0–M2 retain their existing meaning. The previous local-persistence M3 is replaced; the old delivery M4 and optional-platform M5 become M6 and M7. Older session records retain their historical numbering.

| Milestone | Increment | Exit evidence |
| --- | --- | --- |
| M0: walking skeleton | Launch bundled News List, build and verify in GitHub Actions | Real launch assertion, deliberate failure detected locally and in CI, retained reports, CI debug APK installed and smoke-tested. |
| M1: navigation | Select Detail, go back, select another article; render empty List | UI journeys prove matching content and back navigation; isolated empty-screen test. |
| M2: session bookmarks | Add/remove bookmarks, reflect them across List/Detail | Fast membership/independence tests and UI toggle, navigation-retention, and row-indicator checks. No durability claim. |
| M3: list API | First successful list response, then loading, empty, failure and explicit retry | Real HTTP adapter against local test server; controlled state tests; UI wiring journey. PR tests require no live service. |
| M4: detail API | Fetch the selected article by ID, then error/not-found handling | Request-path and decoding tests; detail UI journey; controlled tests prevent late responses from displaying the wrong article. |
| M5: backend bookmarks | Load current-user bookmarks, then confirmed add/remove and reload | Controlled read/write failures; real HTTP adapter tests; load/mutate/reload journey; separate authorised backend evidence. Auth implementation and offline queueing remain deferred. |
| M6: delivery rehearsal | Identify, install, and upgrade a CI-produced candidate | Release manifest, authorised private-channel rehearsal, read/bookmark smoke checks, and server-state reload after upgrade. Signing limitations explicit. |
| M7: optional second platform | Exercise adopted article and bookmark behavior on iOS | Shared tests on iOS and real platform HTTP/UI checks on macOS. |

Implement one example at a time. M3 is the next learning slice, not permission to implement all milestones.

## Next acceptance examples

These are scenario notes, not a request to generate a whole suite:

```gherkin
# M3: first success
Given the article service returns "Morning update" and "Science report"
When I open News List
Then both titles appear in response order

# M3: empty result, selected later
Given the article service returns a successful empty list
When loading finishes
Then I see "No news available"

# M3: failure and retry, selected later
Given loading articles fails
When I open News List
Then I see an error and a Retry action
When I retry and the service returns articles
Then the article titles appear

# M4: selected detail
Given News List contains "Science report"
When I select it and its detail request succeeds
Then I see its title and body

# M5: proposed confirmed write
Given the current user's article is unbookmarked
When I request a bookmark and the service confirms success
Then List and Detail show it as bookmarked
When I relaunch and reload that user's bookmarks successfully
Then it remains bookmarked
```

A local server simulates agreed responses and proves client behavior; it does not prove the actual backend implements that agreement. Track live-service verification separately.

Use [the development loop](02-development-loop.md), [test strategy](03-test-strategy.md), [delivery plan](04-delivery.md), and [session template](05-practice.md). This document update authorises no implementation, account creation, deployment, or publishing.
