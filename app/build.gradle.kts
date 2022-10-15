plugins {
    id(BuildPlugins.androidApplication)
//    id(BuildPlugins.gmsGoogleServices)
    id(BuildPlugins.onesignalGradle)
//    id(BuildPlugins.googleFirebaseCrashlytics)
    id(BuildPlugins.kotlinAndroid)
    id(BuildPlugins.kotlinAndroidExtensions)
    id(BuildPlugins.kotlinKapt)
    id(BuildPlugins.navigationSafeArgs)
}

android {
    /*signingConfigs {
        register("release") {
            storeFile = file(KeyStore.storeFile)
            storePassword(KeyStore.storePassword)
            keyAlias(KeyStore.keyAlias)
            keyPassword(KeyStore.keyPassword)
        }
        register("devdebug") {
            storeFile = file(KeyStore.storeFile)
            storePassword(KeyStore.storePassword)
            keyAlias(KeyStore.keyAlias)
            keyPassword(KeyStore.keyPassword)
        }
        register("devrelease") {
            storeFile = file(KeyStore.storeFile)
            storePassword(KeyStore.storePassword)
            keyAlias(KeyStore.keyAlias)
            keyPassword(KeyStore.keyPassword)
        }
    }*/

    compileSdkVersion(AndroidSdk.compile)
    defaultConfig {
        applicationId = "com.fidilaundry.app"
        minSdkVersion(AndroidSdk.minSdk)
        targetSdkVersion(AndroidSdk.target)
        versionCode(AndroidSdk.versionCode)
        versionName(AndroidSdk.versionName)
        multiDexEnabled = true
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        lintOptions {
            isCheckReleaseBuilds = false
            isAbortOnError = false
        }

//        signingConfig = signingConfigs.getByName("devrelease")
    }

    buildFeatures{
        dataBinding = true
        viewBinding = true
    }

    buildTypes {
        named("debug") {
            isMinifyEnabled = false
            isDebuggable = true
            extra["enableCrashlytics"] = false
            extra["alwaysUpdateBuildId"] = false

            buildConfigField("String", "BASE_URL", "\"${Url.urlDev}\"")
            buildConfigField("String", "API_BASE_URL", "\"${Url.apiBaseDev}\"")
            buildConfigField("String", "API_GTM", "\"${Url.apiGtm}\"")
            buildConfigField("String", "API_DISTRICT", "\"${Url.apiDistrict}\"")
            buildConfigField("String", "API_GEO", "\"${Url.apiGeo}\"")
            buildConfigField("String", "DB_NAME", "\"${Url.dbName}\"")

            defaultConfig {
                manifestPlaceholders.putAll(mapOf(
                    "onesignal_app_id" to "58841145-2ef1-4393-852a-dfdae61205ee",
                    "onesignal_google_project_number" to "331937417858"
                ))
            }

//            signingConfig = signingConfigs.getByName("debug")
        }

        named("release") {
            isMinifyEnabled = true
//            signingConfig = signingConfigs.getByName("release")
            isDebuggable = false
            isJniDebuggable = false
            isRenderscriptDebuggable = false
            isPseudoLocalesEnabled = false
            isZipAlignEnabled = false
            isShrinkResources = true
            setProguardFiles(listOf(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro"))

            buildConfigField("String", "BASE_URL", "\"${Url.urlDev}\"")
            buildConfigField("String", "API_BASE_URL", "\"${Url.apiBaseDev}\"")
            buildConfigField("String", "API_GTM", "\"${Url.apiGtm}\"")
            buildConfigField("String", "API_DISTRICT", "\"${Url.apiDistrict}\"")
            buildConfigField("String", "API_GEO", "\"${Url.apiGeo}\"")
            buildConfigField("String", "DB_NAME", "\"${Url.dbName}\"")

            defaultConfig {
                manifestPlaceholders.putAll(mapOf(
                    "onesignal_app_id" to "58841145-2ef1-4393-852a-dfdae61205ee",
                    "onesignal_google_project_number" to "331937417858"
                ))
            }
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    /* Core Android */
    implementation(Libraries.appCompat)

    /* Andorid UI */
    implementation(Libraries.cardview)
    implementation(Libraries.androidMaterial)
    implementation(Libraries.recyclerview)
    implementation(Libraries.palleteKtx)
    implementation(Libraries.sdp)
    implementation(Libraries.constraintLayout)
    implementation(Libraries.gifDrawable)

    /* GSON */
    implementation(Libraries.gson)

    /* Retrofit */
    implementation(Libraries.retrofit)
    implementation(Libraries.retrofitGson)
    implementation(Libraries.retrofitScalars)

    /* Glide */
    implementation(Libraries.glide)
    kapt(Libraries.glideCompiler)

    /* Multi Dex */
    implementation(Libraries.multidex)

    /* KTX */
    implementation(Libraries.coreKtx)
    implementation(Libraries.lifecycleVM)
    implementation(Libraries.lifecycleExt)

    implementation(Libraries.stdlibKotlin)

    /* One Signal */
    implementation(Libraries.onesignal)

    /* Couroutine */
    implementation(Libraries.coroutinesCore)
    implementation(Libraries.coroutinesAndroid)

    /* Location */
    implementation(Libraries.lifecycleLiveData)
    implementation(Libraries.lifecycleRuntime)

    /* Koin */
    implementation(Libraries.koinAndroid)
    implementation(Libraries.koinAndroidScope)
    implementation(Libraries.koinAndroidViewModel)
    implementation(Libraries.koinAndroidExt)

    /* Room DB */
    implementation(Libraries.roomRuntime)
    kapt(Libraries.roomCompiler)
    implementation(Libraries.roomKtx)

    /* Logger */
    implementation(Libraries.timber)

    /* Date Time*/
    implementation(Libraries.joda)

    /* Local DB */
    implementation(Libraries.paperdb)

    /* Permission */
    implementation(Libraries.dexter)

    /* Paging */
    implementation(Libraries.pagingRuntime)

    /* Navigation */
    implementation(Libraries.navFragment)
    implementation(Libraries.navUI)

    /* CircleImage */
    implementation(Libraries.circleImgView)

    /* OK HTTP */
    implementation(Libraries.okhttp)
    implementation(Libraries.okhttpLogging)
    implementation(Libraries.retrofitMock)

    /* Lottie */
    implementation(Libraries.lottie)

    /* JSOUP */
    implementation(Libraries.jsoup)

    /* Shimmer Load */
    implementation(Libraries.shimmer)

    /* Preferences */
    implementation(Libraries.preferenceKtx)

    testImplementation(TestLibraries.junit4)
    androidTestImplementation(TestLibraries.testRunner)
    androidTestImplementation(TestLibraries.espresso)

}
repositories {
    mavenCentral()
}

 //googleServices { isDisableVersionCheck = true }
