// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    repositories {
        google()
        jcenter()
        maven("https://plugins.gradle.org/m2/")

        maven {
            authentication {
                create<BasicAuthentication>("basic")
            }
            url = uri("https://api.mapbox.com/downloads/v2/releases/maven")
            credentials {
                username = "mapbox"
                password = (project.properties["MAPBOX_DOWNLOADS_TOKEN"] ?: "") as String?
            }
        }
    }
    dependencies {
        classpath (BuildPlugins.androidGradlePlugin)
        classpath (BuildPlugins.kotlinGradlePlugin)
        classpath (BuildPlugins.butterknife)
//        classpath (BuildPlugins.googleService)
//        classpath (BuildPlugins.firebaseCrashlytic)
        classpath (BuildPlugins.onesignal)
        classpath (BuildPlugins.spotlessPlugin)
        classpath (BuildPlugins.navigationArgsPlugin)
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        maven("https://www.jitpack.io")
        maven("https://maven.fabric.io/public")
        maven("https://maven.google.com")

        maven {
            authentication {
                create<BasicAuthentication>("basic")
            }
            url = uri("https://api.mapbox.com/downloads/v2/releases/maven")
            credentials {
                username = "mapbox"
                password = (project.properties["MAPBOX_DOWNLOADS_TOKEN"] ?: "") as String?
            }
        }
    }
}

tasks.register("clean",  Delete::class)  {
    delete(rootProject.buildDir)
}
