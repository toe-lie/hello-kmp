# Test strategy

## Choose tests by risk

The [Practical Test Pyramid](https://martinfowler.com/articles/practical-test-pyramid.html), by Ham Vocke, argues for feedback at multiple granularities and moving checks downward when higher-level execution adds no confidence. Our policy is to use the cheapest boundary that can expose the relevant defect, while retaining a few complete journeys. There is no required percentage split.

An acceptance test describes a business expectation; it can run at an application boundary or through a UI. An end-to-end test exercises the whole declared path. Name substituted boundaries explicitly: a UI test against a fake data source is not evidence of HTTP mapping or backend persistence.

| Risk in this app | Primary check | Additional evidence |
| --- | --- | --- |
| Wrong article opens or back navigation fails | UI journey through the real navigation setup | Open a second article after returning to the list |
| Bookmark toggle affects the wrong article or leaves stale state | Fast behavior tests using real logic | One toggle, back, and reopen UI journey |
| Empty collection renders incorrectly | Controlled empty-data UI test | Visible “No news available” message |
| Wrong request path or JSON mapping | Real HTTP client and adapter against a local test server | Explicit request assertions and agreed response fixtures |
| Loading, empty, failure, or retry rendered incorrectly | State-holder tests with controlled outcomes | Representative UI wiring tests |
| Late detail response replaces the currently selected article | Controlled completion order/cancellation test | Navigate between real destinations |
| Bookmark write claims success before confirmation | Controlled pending, success, and failure tests | Real HTTP mutation mapping and UI feedback |
| Actual backend differs from fixtures | Separate authorised smoke/contract verification | Service version/environment and observed responses recorded |
| Upgrade breaks server bookmark reload | Install candidate over previous build and reload controlled user's state | No local schema migration test unless a database is deliberately introduced |
| App cannot start on a supported target | Packaged app launch on that target | Device exploration before release |

## Doubles and real dependencies

Use real value objects and cheap deterministic collaborators by default. A stub supplies an answer, a fake supplies a simplified working implementation, and a mock verifies an expected interaction. Android's [test-double guide](https://developer.android.com/training/testing/fundamentals/test-doubles) explains these distinctions and dependency replacement.

For M3–M4, use a fake article source for deterministic presentation outcomes and the real HTTP adapter against an isolated local server for transport mapping. Test overlapping contracts without building a second backend. At M5, a fake bookmark source helps simulate pending writes and failures but cannot prove server persistence.

For actual backend verification, run a separate authorised contract/smoke check. Document who owns the service contract and how response examples are checked against it. A stubbed response is evidence of client behavior only. Do not rely on a public service, real account credentials, or a hosted test environment for required PR tests.

Interaction assertions are appropriate when the interaction itself matters: for example, a bookmark save must target the selected article ID. Avoid specifying incidental getter calls or private sequencing. Wrap third-party behavior in a small application-owned boundary only when needed, then verify the adapter with the real library.

## Deterministic execution

- Each test owns its data and cleanup. Use isolated fake state and local test-server instances; no shared mutable singleton fixtures or test-order dependencies.
- Inject time and identifier generation when relevant. Seed randomness and include the seed in failures.
- For coroutine code, control scheduling through the chosen test library and dispatchers. Control completion order and cancellation of reads and writes; avoid real-time waits.
- For UI/device checks, use observable readiness with a deadline, not fixed sleeps. A timeout is a failure with diagnostics.
- Local and PR suites use deterministic article fixtures and controlled HTTP responses. Local test-server networking is allowed; public-service availability must not determine PR results. A device/emulator needs an explicit route to its test server; record that configuration when introduced.
- Reset emulator state between isolated journeys. Within restart/upgrade journeys, preserve the relevant controlled user/session and server data. Do not mistake a cached value for a verified server reload.

## Assertions worth maintaining

Name tests after a behavior and condition. Keep the relevant input and expected result visible. Assert meaningful outputs and side effects, including unchanged bookmark state for other articles. Do not derive expected results by calling the same production algorithm being tested.

Avoid exhaustive tests of generated accessors, framework internals, or trivial forwarding. Prefer a handful of representative examples plus boundaries. Introduce property-based tests only for a useful invariant, such as bookmark changes never altering article content, identity, or list order.

Coverage identifies code you have not exercised; it does not measure assertion quality. At M2, deliberately make bookmark removal do nothing and verify that an appropriate test fails, then restore the code. Later use mutation tooling if supported; inspect surviving mutations rather than chasing a score.

## Mobile checks and suite health

As platform features appear, test lifecycle interruption, process recreation, navigation, accessibility labels, and relevant device/OS variation. Android's [what-to-test guidance](https://developer.android.com/training/testing/fundamentals/what-to-test) discusses edge cases and the separation of local and device tests. Supplement automation with brief exploratory sessions for back navigation, large text, touch targets, and real-device interaction.

A flaky result stays a failure on the original run. Capture logs, screenshots, test data, and seed before a diagnostic rerun. If quarantine is necessary, record an owner, explanation, expiration, and replacement confidence; keep the quarantined test visible in a separate job. Do not quarantine the only check for a critical journey and still claim that journey is verified.

Review tests after each milestone: what defects did they catch, which failures were misleading, and which tests broke only because implementation structure changed?
