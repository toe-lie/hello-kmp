# Project brief and milestones

## News List and Detail

A simple mobile app with exactly two screens: **News List** and **News Detail**, with a bookmark action. The purpose is to practise testing, navigation, and delivery with very little product complexity.

Use a small, fixed set of bundled articles so the app works offline and tests need no public API. Local bookmark persistence is a later exercise. Exclude article creation or editing, login, search, filters, categories, sharing, notifications, remote feeds, synchronization, and elaborate visual design. There is no separate bookmarks screen.

### The two screens

1. **News List:** show article titles and their bookmark indicators in a fixed order. Selecting an article opens its detail screen.
2. **News Detail:** show the selected article's title and body, a bookmark toggle, and back navigation to the list.

The bookmark toggle lives on News Detail; News List reflects its state. Empty and save-error states appear within these screens, not as additional destinations.

### Behavior rules for practice

These are project decisions chosen to keep the exercise small:

- Each bundled article has a stable, distinct ID, a title, and a body. Selection and bookmarks use the ID, not the title.
- The list preserves the bundled article order. Bookmarking never reorders it or changes article content.
- Selecting a row opens the matching article. Back returns to the list; selecting another row shows that article's content and bookmark state.
- Articles start unbookmarked. Tapping Bookmark marks that article; tapping Remove bookmark unmarks it. Other articles are unaffected.
- Bookmark changes are visible when returning to the list and reopening the detail screen during the same session.
- Bookmark state is memory-only until M3. From M3 onward, a successful change survives process termination and relaunch, including removal of a bookmark.
- At M3, a failed save shows an inline error and retains the previously saved bookmark state. The user can retry the same action; the UI must not claim the change succeeded.
- An empty article collection shows “No news available” on the list. This is a controlled test case, not a reason to add a feed or refresh feature.

## Stack decision

Kotlin Multiplatform, Android first and iOS second, remains a suggested route rather than a confirmed requirement. Use a familiar stack if learning KMP would compete with learning testing. The two-screen scope applies whichever stack is chosen.

If using KMP, start with shared Kotlin behavior and a small Android UI. Choose one UI approach at setup and one local persistence mechanism when M3 needs it. Select compatible stable tool versions at implementation time and record exact build commands. Do not introduce a database or repository hierarchy before a real boundary needs it.

Common tests and platform tests serve different purposes; run shared behavior on supported targets as they are introduced. Consult the official [KMP testing tutorial](https://kotlinlang.org/docs/multiplatform-run-tests.html) during setup. Folder and task names depend on the generated project and plugin configuration.

## Milestones with exit evidence

Each row may take several sessions. Keep a short scenario list and implement one example at a time using [the development loop](02-development-loop.md). Later milestones are a backlog, not permission to build the whole app at once.

| Milestone | Smallest useful increment | Exit evidence and lesson |
| --- | --- | --- |
| M0: walking skeleton | Install and launch Android into News List with a few bundled article titles | One UI assertion runs locally and in CI; deliberately breaking the displayed title fails it. CI builds an installable debug artifact tied to a commit. Learn runner, packaging, and feedback setup. |
| M1: navigation | Select an article, read its detail, and return to the list | One UI journey selects an article, checks its title and body, goes back, and opens a different article. Add the empty-list example separately. Learn which behavior needs real navigation wiring to prove it. |
| M2: bookmark interaction | Toggle a bookmark on News Detail and see its state on News List during the same session | Fast behavior tests cover adding, removing, and article independence; one UI journey verifies the toggle and state across back/reopen navigation. Memory-only storage is explicit. Learn state ownership and useful test boundaries. |
| M3: durable bookmarks | Save a bookmark change, terminate the process, and relaunch with the same state | Real storage integration tests cover add/remove with close/reopen; a process-restart UI journey proves wiring; a controlled write-failure test proves the previous state and error are shown. Refactor only after green. Learn what a fake cannot prove. |
| M4: delivery rehearsal | Install a CI-produced build on a device and upgrade it with another build | Preserved-bookmark upgrade check, schema migration test if a schema change exists, and a release manifest. Rehearse the delivery runbook within the authorised scope. |
| M5: optional second platform | Repeat list/detail navigation and bookmark journeys on iOS | Shared tests execute on an iOS simulator; real iOS persistence and UI smoke checks run on macOS CI. Learn platform-specific risk without adding features or screens. |

M0 proves only the boundaries present then. Add navigation evidence at M1, bookmark behavior at M2, and real persistence evidence at M3. The product is complete after M3; M4 practises delivery, and M5 is optional platform practice. Networking is outside this project scope.

## First acceptance examples

Keep these as scenario notes initially, not an entire executable suite:

```gherkin
# M1: navigation
Given the news list contains "Morning update" and "Science report"
When I select "Science report"
Then I see the title and body of "Science report" on News Detail
When I navigate back
Then I see News List with both articles in their original order

# M2: bookmark within a session
Given "Science report" and "Morning update" are unbookmarked
And I am viewing the detail of "Science report"
When I tap Bookmark and navigate back
Then "Science report" is marked as bookmarked in News List
And "Morning update" remains unbookmarked
When I reopen "Science report"
Then its action is Remove bookmark

# M2: remove a bookmark
Given "Science report" is bookmarked
When I open its detail and tap Remove bookmark
And I navigate back
Then "Science report" is no longer marked as bookmarked

# M3: persistence
Given I have successfully bookmarked "Science report"
When the app process is terminated and relaunched without clearing its data
Then "Science report" is still marked as bookmarked

# M3: failed save
Given "Science report" is unbookmarked
And the next bookmark save will fail
When I open its detail and tap Bookmark
Then I see a save error
And "Science report" remains unbookmarked
And the Bookmark action is available to retry
```

At M3, also select an example proving a removed bookmark stays removed after relaunch. An activity recreation alone does not prove survival across process death. A force-stop/relaunch proves process durability, not survival of a sudden power failure.

## Using the other guides

Use [the development loop](02-development-loop.md) for one-example-at-a-time practice, [the test strategy](03-test-strategy.md) to choose boundaries, [the delivery plan](04-delivery.md) for build evidence, and [the session template](05-practice.md) to record learning. Navigation starts at M1, bookmarking at M2, and persistence at M3. Networking is outside scope.

Start with M0 only. Finish with one meaningful UI assertion, evidence that it detects broken behavior, a passing build, and reproducible commands. No application implementation or publishing is authorised by this documentation change.
