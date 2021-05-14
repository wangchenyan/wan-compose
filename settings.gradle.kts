dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        jcenter() // Warning: this repository is going to shut down soon
        maven { setUrl("https://jitpack.io") }
    }
}
rootProject.name = "wanandroid-compose"
include(":app")
 