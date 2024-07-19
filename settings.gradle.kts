pluginManagement {
    repositories {
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
        jcenter()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io")
        jcenter()
    }
}

rootProject.name = "GsrRelaxApp"
include(":app")
include(":api_bluetooth_scan")
include(":impl_bluetooth_scan")
include(":api_bluetooth_scan_screen")
include(":impl_bluetooth_scan_screen")
include(":api_ble_communication")
include(":impl_ble_communication")
include(":api_main_screen")
include(":impl_main_screen")
