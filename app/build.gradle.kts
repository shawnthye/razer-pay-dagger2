plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
    id("kotlin-android")
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

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_1_8
            targetCompatibility = JavaVersion.VERSION_1_8
        }

        vectorDrawables {
            useSupportLibrary = true
        }

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
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.7")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.7")

    //Dagger
    implementation("com.google.dagger:dagger:${Versions.DAGGER}")
    kapt("com.google.dagger:dagger-compiler:${Versions.DAGGER}")

    //Dagger Android
    implementation("com.google.dagger:dagger-android:${Versions.DAGGER}")
    implementation("com.google.dagger:dagger-android-support:${Versions.DAGGER}")
    kapt("com.google.dagger:dagger-android-processor:${Versions.DAGGER}")

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
    implementation("com.google.android.material:material:1.1.0")
    implementation("androidx.constraintlayout:constraintlayout:1.1.3")

    // Http
    implementation("com.squareup.okhttp3:okhttp") {
        version {
            strictly(Versions.OKHTTP)
        }
    }
    implementation("com.squareup.okhttp3:logging-interceptor:${Versions.OKHTTP}")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")

    //Tests
    testImplementation("junit:junit:4.13")
    androidTestImplementation("androidx.test.ext:junit:1.1.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.2.0")

}

apply(plugin = "com.google.gms.google-services")