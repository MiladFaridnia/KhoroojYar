pluginManagement {
    repositories {
        maven("https://en-mirror.ir")
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
        maven("https://jitpack.io")

    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        maven("https://en-mirror.ir")
        google()
        mavenCentral()
        maven("https://jitpack.io")

    }
}

rootProject.name = "KhoroojYar"
include(":app")
 