import java.net.URI

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
        maven { url = URI.create("https://jitpack.io") }
        maven { url = URI.create("https://oss.sonatype.org/content/repositories/snapshots/") }
    }
}

rootProject.name = "Mandiri News"
include(":app")
