pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "android-clean-architecture-sample"
include(":app")
include(":Domain:core-usecase")

include(":Data:core-data")
include(":Data:core-model")
include(":mylibrary")
includeBuild("../scoutivi-foundation/Tools/gradle-plugin/plugin-dependency")
 