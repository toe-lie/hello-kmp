# Local commands

Run from the project root using the checked-in Gradle wrapper. Android builds require a configured JDK and Android SDK; device tests require an authorised connected device or running emulator. Dependency downloads may require network access. These commands do not verify iOS or CI.

| Purpose | Command | Evidence so far |
| --- | --- | --- |
| Build debug APK | `./gradlew :androidApp:assembleDebug` | Developer reported baseline build success; no fresh standalone result recorded for this slice. |
| Android host tests | `./gradlew :shared:testAndroidHostTest` | Developer supplied baseline success; post-feature rerun not yet reported. |
| Focused news UI test | See below | Developer supplied red and green output on HMD ARC, Android 14; one test executed. |

```sh
./gradlew :androidApp:connectedDebugAndroidTest \
  -Pandroid.testInstrumentationRunnerArguments.class=dev.toelie.hellokmp.NewsListTest
```

The pasted developer command used `:android:connectedDebugAndroidTest`; its failure output identified the actual task as `:androidApp:connectedDebugAndroidTest`. Use the full module name above for repeatability.

UI report: `androidApp/build/reports/androidTests/connected/debug/index.html`.
Debug APK directory: `androidApp/build/outputs/apk/debug/`.

Toolchain declarations live in `gradle/libs.versions.toml`, `gradle/wrapper/gradle-wrapper.properties`, and module build files. Record the actual JDK/SDK/device setup and verify a clean checkout before calling setup reproducible. Storage and iOS commands will be added when those checks are introduced.
