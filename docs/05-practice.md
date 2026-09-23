# Practice sessions and review templates

## Suggested cadence

Use 45–90 minute sessions as a starting point. Spend a few minutes choosing examples, most of the session in short feedback cycles, and the last few minutes recording what changed in your understanding. This is a practice suggestion, not a time limit on difficult work.

Read just enough to answer the current question. Pair M0 with GOOS chapters 4 and 10–11; revisit chapter 5 during M1–M2, chapter 25 during M3, and chapters 26–27 if asynchronous storage work needs further study. These chapter topics are listed in the [author-maintained contents](https://growing-object-oriented-software.com/toc.html). Reading is preparation for an experiment, not a prerequisite exam.

## Session record

Copy this into a dated file under `docs/sessions/` when coding begins:

```markdown
# Session: <date and behavior>
Mode: coach / pair / implement
Milestone:
User-visible outcome:
Out of scope:

## Scenario list
- [ ] <condition, action, expected outcome>

## Evidence for the selected example
Test boundary and what is real/faked:
Command:
Observed red result and why it is the intended failure:
Smallest implementation change:
Observed green result:
Refactor, if any, and recheck:
Wider checks run / not run and reasons:

## Learning
What did the test make me decide?
What defect would this assertion detect?
What remains unproven?
Next small step:
```

Keep evidence brief. A record per meaningful cycle is useful; a transcript of every keystroke is not. Never reconstruct failure evidence from memory as though it were captured execution.

## Prompts for an agent

**Coach:** “Help me practise M1. Give me a short behavior list and suggest the next example. Let me write the test; review my attempt before showing a solution.”

**Test review:** “Review whether this test would detect the stated defect. Identify implementation coupling and missing assertions. Do not modify files.”

**Implementation:** “Implement the bookmark-removal slice using our workflow. Observe and record the intended failing test before the change, complete the slice, and report verification.”

**Design exercise:** “Suggest an internal refactor that preserves navigation and bookmark behavior. Help me predict which tests should remain unchanged, then review the result.”

**Pipeline exercise:** “Implement the current milestone's CI checks. Prove test discovery and failure propagation. Document exact local equivalents and report unavailable checks.”

## Definition of done for a behavior slice

- Acceptance examples are satisfied at the stated boundary.
- Important failure/boundary cases have appropriate evidence; any deferred case is explicit.
- Required checks pass; unavailable checks are reported as incomplete verification.
- Test data and execution are isolated and deterministic enough for repeatable runs.
- The change contains no unrelated feature work or speculative scaffolding.
- Behavior and command documentation reflect the implementation.
- You can explain why each added test exists and one thing it cannot prove.

## Milestone exercises

1. M1: deliberately open the wrong article and prove the navigation test detects it. Restore the code and verify green.
2. M2: temporarily make bookmark removal do nothing and prove a test detects it. Restore the code and verify green.
3. M3: compare fake-store evidence with real storage close/reopen tests. Identify a bug only the real adapter test could catch. Then try a small internal refactor and investigate any test edits it requires.
4. M4: produce a broken pipeline run on a temporary branch. Verify required checks block promotion and diagnostics are sufficient.
5. M5: name a platform difference shared tests do not cover, then add the smallest useful platform check.

After each milestone, review feedback time, unexplained failures, escaped defects, and your own ability to select the next test. Count completed learning exercises rather than maximising test count or coverage percentage.

## Commit checkpoint

Commit a coherent, verified slice: its behavior, test, required configuration, and supporting documentation belong together. Do not create separate shared-history commits for every red/green step. Record red evidence in the session note and commit the restored, passing implementation. No refactor is required when the code is already clear.

Before committing:

1. Review the production change and assertions. Remove accidental edits and unused imports.
2. Run the focused test and affected fast tests. Record actual results and any remaining checks; a local commit does not establish milestone or release completion.
3. Inspect `git status --short` and `git diff`. Stage explicit paths for the slice using `git add <paths>`; use `git add -p` when a tracked file contains unrelated changes.
4. Inspect `git diff --cached --stat`, `git diff --cached`, and `git diff --cached --check`. The staged snapshot is what the commit records. Review new files here too; ordinary `git diff` omits untracked files.
5. Commit, then inspect `git log -1 --oneline` and `git status --short`. Understand anything left unstaged.

Use a short imperative subject describing the outcome, such as `Show bundled news titles on launch`. An optional body explains why, meaningful test evidence, and limitations. Avoid vague subjects such as `updates`, or claiming all of M0 is complete. Conventional Commit prefixes are optional unless the team adopts them.

If the project has no Git repository yet, initialise it with `git init`. Review `.gitignore` and use `git status --short --untracked-files=all` to inspect candidates before staging. Keep machine-local configuration, build outputs, IDE state, and credentials out; include the Gradle wrapper. Stage the project files intentionally and review the complete staged snapshot. An honest first subject is `Initialize news app with tested launch screen`. Do not reconstruct a fictional template-only baseline after the feature already exists.

A commit is a local checkpoint. Pushing, opening a PR, and merging are separate steps. Keep CI work as a subsequent coherent change if it is not ready yet.
