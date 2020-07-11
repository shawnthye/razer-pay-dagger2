plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
}

object Flavors {
    const val ENVIRONMENT_DIMENSION = "environment"
}


android {
    compileSdkVersion(Versions.COMPILE_SDK)

    defaultConfig {
        applicationId = "com.example.u2020"
        minSdkVersion(Versions.MIN_SDK)
        targetSdkVersion(Versions.TARGET_SDK)
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    flavorDimensions(Flavors.ENVIRONMENT_DIMENSION)
    productFlavors {
        create("internal") {
            setDimension(Flavors.ENVIRONMENT_DIMENSION)
            applicationIdSuffix = ".internal"
        }
        create("production") {
            setDimension(Flavors.ENVIRONMENT_DIMENSION)
        }
    }
}


dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation("org.jetbrains.kotlin:kotlin-stdlib:${Versions.KOTLIN}")
    implementation("androidx.core:core-ktx:1.3.0")

    //Dagger
    implementation("com.google.dagger:dagger:${Versions.DAGGER}")
    kapt("com.google.dagger:dagger-compiler:${Versions.DAGGER}")

    // Room
    implementation("androidx.room:room-runtime:${Versions.ROOM}")
    kapt("androidx.room:room-compiler:${Versions.ROOM}")
    implementation("androidx.room:room-ktx:${Versions.ROOM}") // Kotlin Extensions and Coroutines support for Room
    androidTestImplementation("androidx.room:room-testing:${Versions.ROOM}")

    // Firebase
    implementation("com.google.firebase:firebase-analytics:17.4.4")
    implementation("com.google.firebase:firebase-messaging:20.2.3")

    // UI
    implementation("androidx.appcompat:appcompat:1.1.0")
    implementation("androidx.constraintlayout:constraintlayout:1.1.3")

    //Tests
    testImplementation("junit:junit:4.13")
    androidTestImplementation("androidx.test.ext:junit:1.1.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.2.0")

}

apply(plugin = "com.google.gms.google-services")