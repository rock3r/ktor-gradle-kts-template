## Ktor template project

This is a template project for your Ktor projects. It uses the Gradle Kotlin DSL, and has two simple modules,
[`api-models`](/api-models), and [`api-server`](/api-server), which contain the API data models and the Ktor
server skeleton, respectively.

There is not much specifically that makes this a Ktor-only template: you can easily use it for general-purpose
Kotlin projects by removing the modules you don't need and creating new ones, following the general structure.

#### Gradle build setup

The goal is to avoid repetition, so most configuration is done in the [root `build.gradle.kts` file](build.gradle.kts).
In there, the template makes abundant use of the `subprojects` and `allprojects` closures to configure the child modules.
As a result, the `api-models` [build script](/api-models/build.gradle.kts) is empty as it needs no further configuration
besides what it inherits from the root buildscript.

#### Dependencies management

Most dependencies are defined by [`Dependencies`](buildSrc/src/main/kotlin/Dependencies.kt) and
[`DependencyVersions`](buildSrc/src/main/kotlin/DependencyVersions.kt) — should you want to add, change or remove any
dependency, you can do it in there. This enables centralised dependency management and allows the IDE to auto-complete
the dependencies you add to your project. 

#### Windows support
Note that for now Windows is **NOT** supported out of the box, due to the use of symlinks to reuse `/gradle.properties`
into `/buildSrc`. You can make it work on Windows by manually copying and keeping the file in sync into `/buildSrc`.
