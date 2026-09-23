# The development loop

## Two complementary ideas

Kent Beck's [Canon TDD](https://newsletter.kentbeck.com/p/canon-tdd) describes a scenario list, selecting one runnable example, making it and earlier tests pass, optionally improving the design, and repeating. It is not a prescription to implement every test up front.

Freeman and Pryce's [GOOS contents](https://growing-object-oriented-software.com/toc.html) identify a walking skeleton, acceptance tests at feature boundaries, behavior-focused unit tests, and feedback from difficult tests. In this project, outside-in means choosing a visible outcome first, then discovering the responsibilities needed to deliver it. It does not require mocking every collaborator.

The workflow below is our practical adaptation, with two time scales: a feature-level acceptance example can remain red while several focused tests go through their own cycles. That is distinct from writing a batch of speculative unit tests. Keep unfinished red acceptance work local; finish or split the slice before integrating into main.

## One session

1. Select an observable outcome small enough to finish. Write several scenario notes and identify what remains outside the slice.
2. Select one acceptance example. Use a UI test when proving UI wiring; otherwise an application-boundary test may express the rule more cheaply. Record what its boundary omits.
3. Run it. Resolve runner or compile setup separately, then establish the missing behavior through an assertion failure where feasible.
4. Choose the next focused example needed to progress. Write only that test, run it, implement enough behavior, and rerun the relevant passing suite.
5. When green, consider a small refactor. Rerun tests after changing structure. Leaving clear code alone is a valid choice.
6. Repeat focused cycles until the acceptance example passes. Run the appropriate integration and platform checks before declaring the slice complete.
7. Record one lesson and the next uncertainty. Use the [commit checkpoint](05-practice.md#commit-checkpoint) to review and commit a coherent green slice; integrate after required checks pass.

Build scripts, generated scaffolding, and exploratory spikes may need direct work before a behavioral test can run. Label those activities honestly. Timebox a spike and carry the learning into tested production code; do not pretend a spike was test-driven.

## Worked example: bookmark an article

Assume M1 navigation exists. The first outer test opens an article, taps Bookmark, returns to the list, and looks for its bookmark indicator. It initially fails because the action does not mark the article.

Choose a focused example at the application boundary: bookmarking an article exposes its bookmarked state. A possible assertion sketch follows; this is illustrative Kotlin, not an existing API or runnable test:

```kotlin
val bookmarks = Bookmarks()
bookmarks.add("science-report")
assertTrue(bookmarks.contains("science-report"))
```

Make that pass, connect the UI, and rerun the outer test. Then choose removal or article independence as the next example. Keep article IDs distinct from titles. Do not build a repository hierarchy just because bookmarks will eventually persist.

At M3, introduce the storage boundary and prove its real implementation independently. A mocked successful write cannot satisfy the relaunch example.

## Reading design feedback

| Difficulty encountered | Question to investigate |
| --- | --- |
| A simple rule needs an emulator | Is platform work mixed into business behavior? |
| Setup requires many unrelated collaborators | Does the subject have too many responsibilities? |
| A refactor breaks many tests but no behavior | Are assertions coupled to private structure or incidental call order? |
| Every test mocks a database library | Would an application-owned storage boundary plus real adapter tests express the risk better? |
| A fake passes while the real app fails | Which semantics, wiring, or platform assumptions does the fake omit? |

Treat these as diagnostic questions. An interface or new layer is justified only if it improves the actual problem.

For one later exercise, implement the same small operation using real in-memory collaborators and then using interaction expectations at a meaningful boundary. Compare readability and refactoring cost. State-oriented and interaction-oriented tests are tools; neither is a universal design law.
