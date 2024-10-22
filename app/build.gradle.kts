plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.ksp) // Apply KSP for code generation
    alias(libs.plugins.hilt) // Apply Hilt for dependency injection
    id("kotlin-kapt")
}

android {
    namespace = "com.example.testapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.testapp"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    buildFeatures {
        buildConfig = true
        viewBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    // Core dependencies
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.annotation)

    // Testing dependencies
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Lifecycle and ViewModel
    implementation(libs.androidx.lifecycle.livedata.ktx)
        implementation(libs.androidx.lifecycle.viewmodel.ktx)

        // RecyclerView
        implementation(libs.recyclerview)

        // Hilt dependencies
        implementation(libs.hilt.android)
        kapt(libs.hilt.android.compiler) // Use kapt for Hilt annotation processing

        // Coroutines
        implementation(libs.kotlinx.coroutines.android)
        implementation(libs.kotlinx.coroutines.core)

        // Retrofit and Gson
        implementation(libs.retrofit2)
        implementation(libs.retrofit2.converter.gson)
        implementation(libs.gson)

        // Room
        implementation(libs.androidx.room.ktx)
        implementation(libs.androidx.room.runtime) // Room runtime
        kapt(libs.androidx.room.compiler)
//        ksp(libs.androidx.room.compiler)
//
    // OkHttp logging interceptor
        implementation(libs.okhttp3.logging.interceptor)

}
