# Delivery plan and runbook

## What we are practising

CI is frequent integration with automated feedback, not merely a hosted YAML file. DORA recommends small integrations, fast checks, and prioritising broken builds. See [continuous integration](https://dora.dev/capabilities/continuous-integration/).

Continuous delivery keeps a tested build ready for release; continuous deployment automatically releases passing changes. This exercise targets delivery to a private testing channel with a deliberate promotion step. App-store review and device signing remain platform constraints. See [DORA's continuous-delivery guidance](https://dora.dev/capabilities/continuous-delivery/).

The following pipeline is a project proposal. No CI provider, account, signing identity, or distribution destination has been configured. GitHub Actions is a reasonable default if the repository will live on GitHub; confirm hosting when implementation starts.

## Introduce automation progressively

| Trigger | Required work when applicable | Output |
| --- | --- | --- |
| Local edit | Focused test, then affected fast suite | Immediate feedback |
| Pull request | Formatting/static checks, fast behavior tests, real adapter tests, Android build and critical Android UI smoke | Test reports, failure diagnostics, commit identity |
| Main update | Repeat required verification against the merged revision; build candidate | Installable artifact and manifest |
| iOS introduced | Add shared iOS tests, app build, real persistence checks, and critical UI smoke on macOS | iOS results; simulator build or signed candidate as appropriate |
| Scheduled | Wider OS/device cases, extended reliability checks, optional mutation analysis | Reports and follow-up issues |
| Approved release | Verify candidate checks; deliver that candidate to the chosen test channel; install and smoke-test | Distribution record tied to candidate |

M0 needs only checks for its existing behavior and build. Add navigation checks at M1, bookmark checks at M2, and real storage adapter checks at M3. Networking is outside scope. Do not configure empty jobs that misleadingly report protection for nonexistent tests.

Suggested project budgets: focused tests in seconds, fast feedback within five minutes, required platform lanes within fifteen minutes once provisioned. These are starting targets, not sourced guarantees or reasons to skip checks. Measure actual duration and fix the slowest useful bottleneck.

## Reproducible commands

At M0, create a command reference containing:

- Required JDK, SDK, Gradle wrapper, Kotlin/plugin, emulator, and eventually Xcode versions.
- Exact commands for one test, fast tests, storage integration tests, platform UI tests, and packaging.
- Supported host OS, environment setup, expected reports, and artifact locations.
- Which checks require a device, macOS, signing, network access, or external credentials.

Discover actual Gradle tasks in the scaffolded project; do not assume one command runs every target. Commit the wrapper and version declarations, and use dependency locking/verification where supported. Verify setup from a clean checkout before calling the build reproducible. Caches accelerate work; correctness cannot depend on warm caches.

## Pipeline implementation checklist

- Run checks on both pull requests and main. Protect main with the named required jobs once repository hosting supports it.
- Ensure a job fails when its expected tests are absent or not discovered. Confirm this during setup by deliberately breaking a test on a temporary branch.
- Upload reports and failure diagnostics even after a failing test. Record retention and avoid sensitive data.
- Use minimum workflow permissions. Keep untrusted pull-request execution separate from credentials, signing, and publishing. Pin third-party actions to reviewed revisions if using Actions.
- Cancel obsolete verification runs if useful; serialize releases so candidates cannot overtake each other.
- Use distinct identifiers for development and distributed builds. Store credentials in the CI provider's secret facility.
- Fix or revert a broken main before new feature work. Do not merge unfinished failing acceptance tests.

## Candidate and release runbook

1. Select a green main revision. Record commit, build number, toolchain, test results, and artifact checksum in a manifest.
2. Produce the distributable candidate in a trusted job. If signing or packaging changes the tested artifact, identify the transformation and smoke-test the resulting candidate. A simulator bundle is not a device-distribution artifact.
3. Promote the identified candidate to the agreed private channel. Do not silently rebuild from a moving branch during promotion.
4. Install it on a device; verify launch, list/detail navigation, bookmark add/remove, and bookmark state after relaunch. For an upgrade, install over the previous version with data present.
5. Record who/what approved promotion, the destination, build identity, and observed result.
6. If verification fails, stop promotion, preserve evidence, and repair through the normal workflow.

For a bad distributed mobile release, halt further rollout and prepare a corrected build. Do not assume installed apps can be downgraded or migrated databases rolled back. Prefer compatible schema evolution; prove recovery with previous-version fixtures. A backend is outside this project’s scope.

## Completion criteria

M4 is complete when you can start from a clean checkout, reproduce required checks, identify the exact binary under test, install it, and upgrade without losing bookmark state. Uploading a debug artifact proves packaging, not signed release readiness. Record signing/account limitations explicitly if private distribution cannot yet be exercised.
