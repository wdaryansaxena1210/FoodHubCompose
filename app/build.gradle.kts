plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")


    //serialization
    //uncomment below and add the second commented plugin instead
    kotlin("plugin.serialization") version "2.1.20"
    //id("org.jetbrains.kotlin.plugin.serialization") version "1.9.10"
}


android {
    namespace = "com.example.foodhub_android"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.foodhub_android"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        resValue("string", "google_maps_key", project.findProperty("GOOGLE_MAPS_API_KEY").toString())

    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }

    flavorDimensions += "environment"

    productFlavors{
        create("customer"){
            dimension = "environment"
        }
        create("restaurant"){
            dimension = "environment"
            applicationIdSuffix = ".restaurant"
            resValue("string", "app_name", "FH Restaurant")
        }

        create("rider"){
            dimension = "environment"
            resValue("string", "app_name", "FH Rider")
        }
    }

    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    //Splash Screen
    implementation(libs.core.splashscreen)

    //hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)
    implementation(libs.androidx.hilt.navigation.compose)

    //retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.logging.interceptor)

    //compose navigation
    implementation(libs.androidx.navigation.compose)

    //lifecycle aware components
    implementation(libs.androidx.lifecycle.runtime.compose)

    //serialization
    implementation(libs.kotlinx.serialization.json)

    //credentials?
    implementation(libs.androidx.credentials)
    implementation(libs.androidx.credentials.play.services.auth)
    implementation(libs.googleid)

    //coil (library for images)
    implementation(libs.coil.compose)
    implementation(libs.coil.network.okhttp)

    //Google Maps
    implementation(libs.android.maps.compose)
}
// Allow references to generated code
kapt {
    correctErrorTypes = true
}

//keytool -keystore path-to-debug-or-production-keystore -list -v