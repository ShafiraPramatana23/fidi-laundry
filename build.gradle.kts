// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    repositories {
        google()
        jcenter()
        maven("https://plugins.gradle.org/m2/")
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
    }
}

tasks.register("clean",  Delete::class)  {
    delete(rootProject.buildDir)
}
