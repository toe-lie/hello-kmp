# Agent collaboration rules

## Purpose and scope

This is a learning project for an experienced mobile developer practising testing and delivery. Optimise for the developer's ability to reason about tests, not code volume.

Read README.md and the relevant documents under docs/ before working. Current user instructions override these defaults. The agreed product is the two-screen news app in docs/01-project.md. The suggested stack remains provisional until adopted; do not silently turn it into a hard requirement.

## Working modes

- **Coach, default:** help select a small behavior, propose a few scenarios, and explain tradeoffs. Leave exercise implementation to the developer. Provide hints or review when requested; do not dump a completed feature as a hint.
- **Pair:** when requested, work on the agreed part and leave a clear next step for the developer.
- **Implement:** when the user delegates implementation, complete the requested slice, including relevant verification. Do not pause at every TDD step for permission.
- **Review:** inspect behavior, tests, and evidence; report actionable findings. Do not rewrite the feature unless asked.

Requests such as “implement M0” authorise implementation of that milestone. Requests for documentation do not authorise scaffolding or publishing an app.

## Implementation discipline

1. Identify the requested outcome and consult the behavior examples in docs/01-project.md.
2. Maintain a short scenario list. Use docs/02-development-loop.md for the test cycle; do not batch-generate the whole test suite and then the application.
3. For each behavior change, record the focused command and observed failure before production implementation, then the passing result. Never invent an earlier red run. If code already exists, call the work regression testing or test-after work accurately.
4. Setup/build failures are infrastructure evidence, not evidence that a behavioral assertion works. Explain which one occurred.
5. Keep changes scoped to the current slice. Introduce seams when a real test or runtime boundary needs them; do not scaffold speculative architectural layers.
6. Use the test strategy to choose real collaborators and doubles. Do not erase assertions, weaken requirements, skip checks, or regenerate snapshots merely to obtain green results.
7. Report commands actually run, results, checks not run, and why. “Should pass” is not “passed.” A failed or unavailable required check means verification is incomplete.
8. Update the session record and command documentation when behavior or tooling changes. Preserve unrelated user changes.

## Delivery boundaries

The delivery document is a future implementation plan. It does not authorise account creation, spending, uploading binaries, publishing, or production changes. Follow the user's requested scope when those activities become relevant.

Do not put secrets in source, logs, fixtures, or artifacts. Do not expose privileged credentials to untrusted pull-request code. A local or CI failure is a reason to investigate, not a reason to bypass a gate.

## Commit-message practice

In coaching mode, let the developer draft commit messages first. Review the draft against the staged scope, explain corrections, and suggest an improvement. Leave committing to the developer unless explicitly delegated.

## Handoff

Summarise the behavior delivered, evidence, remaining limitations, and one useful next exercise. In coaching mode, explain the reasoning without supplying the entire solution unless asked.
