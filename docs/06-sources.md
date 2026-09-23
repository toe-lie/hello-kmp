# Sources and interpretation

References checked on 2026-09-23. This playbook is an original training plan informed by the sources below, not a reproduction of their chapters. The two-screen news app scope is user-selected. Its detailed behavior rules, milestones, timing budgets, collaboration modes, and release exercises are project decisions, not requirements from these sources.

| Source | What it supports here | How to use it |
| --- | --- | --- |
| Kent Beck, [Canon TDD](https://newsletter.kentbeck.com/p/canon-tdd), 11 December 2023 | The scenario-list and single-example programming loop | Revisit when tempted to write all tests first or treat coverage as TDD. Beck presents a definition, not a universal mandate. |
| Steve Freeman and Nat Pryce, [Growing Object-Oriented Software, Guided by Tests](https://growing-object-oriented-software.com/), and [contents](https://growing-object-oriented-software.com/toc.html) | Walking skeleton, acceptance-led development, object collaboration, persistence and asynchronous testing topics | Read the relevant chapters in your copy. The public contents verify topic/chapter mapping; full chapter text was not reviewed for this document. |
| Ham Vocke, [The Practical Test Pyramid](https://martinfowler.com/articles/practical-test-pyramid.html), 26 February 2018 | Test granularity, economical feedback, avoiding redundant higher-level checks | Use to reason about a suite's shape, not mandate numerical ratios. |
| DORA, [Continuous integration](https://dora.dev/capabilities/continuous-integration/) | Frequent integration, automated feedback, fixing broken builds | Evaluate your actual development habits as well as pipeline configuration. |
| DORA, [Continuous delivery](https://dora.dev/capabilities/continuous-delivery/) | Keeping software releasable through delivery capabilities | Distinguish release readiness from automatically deploying every change. |
| Kotlin, [Test your multiplatform app](https://kotlinlang.org/docs/multiplatform-run-tests.html) | Common and platform-specific test execution | Check again when selecting the toolchain; exact generated targets and task names can change. |
| Android Developers, [Use test doubles](https://developer.android.com/training/testing/fundamentals/test-doubles) | Fake/stub/mock roles and replacing dependencies | Apply at real boundaries rather than substituting everything. |
| Android Developers, [What to test](https://developer.android.com/training/testing/fundamentals/what-to-test) | Mobile edge cases and local/device test responsibilities | Add platform checks when the corresponding risk appears. |

## Books mentioned but not precisely identified

The title “Test Driven Development: A Practical Guide” needs an author or edition before we can attribute specific advice reliably. Likewise, “CI/CD” may refer to a topic or a particular book. This guide does not invent chapter references or claim those books were read. Add their bibliographic details later, then map a useful passage to a concrete exercise.

## Where our choices differ from rigid interpretations

Outside-in describes where feature discovery starts; it does not force every rule into a UI test. An outer acceptance example and an inner TDD loop operate at different scopes. Interaction-based design can be useful without requiring mocks for every object. Refactoring is a considered opportunity after green, not mandatory churn. A green test suite supplies evidence about tested behavior, not proof that every important failure has been covered.
