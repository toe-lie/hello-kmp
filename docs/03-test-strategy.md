# Test strategy

## Choose tests by risk

The [Practical Test Pyramid](https://martinfowler.com/articles/practical-test-pyramid.html), by Ham Vocke, argues for feedback at multiple granularities and moving checks downward when higher-level execution adds no confidence. Our policy is to use the cheapest boundary that can expose the relevant defect, while retaining a few complete journeys. There is no required percentage split.

An acceptance test describes a business expectation; it can run at an application boundary or through a UI. An end-to-end test exercises the whole declared path. Name substituted boundaries explicitly: a UI test against a fake repository is not evidence of real persistence.

| Risk in this app | Primary check | Additional evidence |
| --- | --- | --- |
| Wrong article opens or back navigation fails | UI journey through the real navigation setup | Open a second article after returning to the list |
| Bookmark toggle affects the wrong article or leaves stale state | Fast behavior tests using real logic | One toggle, back, and reopen UI journey |
| Empty collection renders incorrectly | Controlled empty-data UI test | Visible “No news available” message |
| Save reports success but loses data | Real database integration test with close/reopen | Process restart journey |
| Existing bookmarks destroyed by an upgrade | Previous-schema fixture migrated with production migration code, if the schema changes | Install old build, bookmark an article, upgrade without uninstalling |
| Failed bookmark save claims success or prevents retry | Application test with controlled write failure; previous saved state is retained | One visible save-error UI test |
| App cannot start on a supported target | Packaged app launch on that target | Device exploration before release |

## Doubles and real dependencies

Use real value objects and cheap deterministic collaborators by default. A stub supplies an answer, a fake supplies a simplified working implementation, and a mock verifies an expected interaction. Android's [test-double guide](https://developer.android.com/training/testing/fundamentals/test-doubles) explains these distinctions and dependency replacement.

For this project, a fake bookmark store is useful for simulating a write failure. It cannot establish real transaction, migration, or durability behavior. Run shared storage-contract examples against the fake and real store where their semantics overlap; retain real-only database tests too. Do not turn a fake into a second production database.

Interaction assertions are appropriate when the interaction itself matters: for example, a bookmark save must target the selected article ID. Avoid specifying incidental getter calls or private sequencing. Wrap third-party behavior in a small application-owned boundary only when needed, then verify the adapter with the real library.

## Deterministic execution

- Each test owns its data and cleanup. Use isolated storage instances; no shared mutable singleton fixtures or test-order dependencies.
- Inject time and identifier generation when relevant. Seed randomness and include the seed in failures.
- For coroutine code, control scheduling through the chosen test library and dispatchers. Control completion of asynchronous storage operations when needed; avoid real-time waits.
- For UI/device checks, use observable readiness with a deadline, not fixed sleeps. A timeout is a failure with diagnostics.
- Local and PR suites use bundled articles and isolated bookmark storage. Networking is outside this app’s scope.
- Reset emulator state between isolated journeys. Within a restart or upgrade journey, deliberately preserve app data.

## Assertions worth maintaining

Name tests after a behavior and condition. Keep the relevant input and expected result visible. Assert meaningful outputs and side effects, including unchanged bookmark state for other articles. Do not derive expected results by calling the same production algorithm being tested.

Avoid exhaustive tests of generated accessors, framework internals, or trivial forwarding. Prefer a handful of representative examples plus boundaries. Introduce property-based tests only for a useful invariant, such as bookmark changes never altering article content, identity, or list order.

Coverage identifies code you have not exercised; it does not measure assertion quality. At M2, deliberately make bookmark removal do nothing and verify that an appropriate test fails, then restore the code. Later use mutation tooling if supported; inspect surviving mutations rather than chasing a score.

## Mobile checks and suite health

As platform features appear, test lifecycle interruption, process recreation, navigation, accessibility labels, and relevant device/OS variation. Android's [what-to-test guidance](https://developer.android.com/training/testing/fundamentals/what-to-test) discusses edge cases and the separation of local and device tests. Supplement automation with brief exploratory sessions for back navigation, large text, touch targets, and real-device interaction.

A flaky result stays a failure on the original run. Capture logs, screenshots, test data, and seed before a diagnostic rerun. If quarantine is necessary, record an owner, explanation, expiration, and replacement confidence; keep the quarantined test visible in a separate job. Do not quarantine the only check for a critical journey and still claim that journey is verified.

Review tests after each milestone: what defects did they catch, which failures were misleading, and which tests broke only because implementation structure changed?
