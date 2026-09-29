# Local commands

Run from the project root using the checked-in Gradle wrapper. Android builds require a configured JDK and Android SDK; device tests require an authorised connected device or running emulator. Dependency downloads may require network access. These commands do not verify iOS or CI.

| Purpose | Command | Evidence so far |
| --- | --- | --- |
| Build debug APK | `./gradlew :androidApp:assembleDebug` | Developer reported local/CI build success and installation of a CI APK. |
| Android host tests | `./gradlew :shared:testAndroidHostTest` | Existing command, including bookmark behavior tests. |
| Full Android UI suite | `./gradlew :androidApp:connectedDebugAndroidTest` | Developer reported nine passing tests through M2; suite size may grow. |
| Focused news UI tests | See below | Useful for local feedback; CI runs the unfiltered suite. |

```sh
./gradlew :androidApp:connectedDebugAndroidTest \
  -Pandroid.testInstrumentationRunnerArguments.class=dev.toelie.hellokmp.NewsListTest
```

The pasted developer command used `:android:connectedDebugAndroidTest`; its failure output identified the actual task as `:androidApp:connectedDebugAndroidTest`. Use the full module name above for repeatability.

UI report: `androidApp/build/reports/androidTests/connected/debug/index.html`.
Debug APK directory: `androidApp/build/outputs/apk/debug/`.

Toolchain declarations live in `gradle/libs.versions.toml`, `gradle/wrapper/gradle-wrapper.properties`, and module build files. Record the actual JDK/SDK/device setup and verify a clean checkout before calling setup reproducible. HTTP adapter/contract and iOS commands will be added when those checks are introduced. No API test server, adapter runner, base URL, or live-service credentials are configured by this document.

## API test commands: pending setup

At M3, record the real commands for adapter tests, local-server startup/cleanup, and emulator/device addressing after verifying them. Keep live-service smoke commands separate and explicitly authorised. Do not present planned commands as passed checks. Dependency downloads are distinct from tests depending on a live backend.
