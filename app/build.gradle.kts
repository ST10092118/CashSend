plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    id("com.google.gms.google-services")

}

android {
    namespace = "com.opsc7311.OPSC7321CashSend"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.opsc7311.OPSC7321CashSend"

    }

    android {
        namespace = "com.example.OPSC7312CashSend"
        compileSdk = 34

        defaultConfig {
            applicationId = "com.example.OPSC7312CashSend"

            minSdk = 24
            targetSdk = 34
            versionCode = 1
            versionName = "1.0"

            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        }

        buildFeatures {
            dataBinding = true
            viewBinding = true
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
            sourceCompatibility = JavaVersion.VERSION_1_8
            targetCompatibility = JavaVersion.VERSION_1_8
        }
        kotlinOptions {
            jvmTarget = "1.8"
        }

    }

    dependencies {

        implementation(libs.androidx.core.ktx)
        implementation(libs.androidx.appcompat)
        implementation(libs.material)
        implementation(libs.androidx.activity)
        implementation(libs.androidx.constraintlayout)
        implementation(libs.firebase.auth)
        implementation(libs.firebase.database.ktx)
        implementation("com.github.bumptech.glide:glide:4.11.0")
        implementation(libs.firebase.storage.ktx)
        annotationProcessor("com.github.bumptech.glide:compiler:4.11.0")
        implementation("com.google.guava:guava:31.1-android")
        testImplementation(libs.junit)
        androidTestImplementation(libs.androidx.junit)
        androidTestImplementation(libs.androidx.espresso.core)
        implementation(libs.androidx.constraintlayout)
        implementation(libs.androidx.navigation.fragment.ktx)
        implementation(libs.androidx.navigation.ui.ktx)
        implementation ("com.google.android.gms:play-services-auth:21.2.0")
        implementation ("androidx.camera:camera-core:1.0.2")
        implementation ("androidx.camera:camera-camera2:1.0.2")
        implementation ("androidx.camera:camera-lifecycle:1.0.2")
        implementation ("androidx.camera:camera-view:1.0.0-alpha31")
        implementation ("com.google.mlkit:barcode-scanning:17.0.3")
        implementation ("com.squareup.retrofit2:retrofit:2.9.0")
        implementation ("com.squareup.retrofit2:converter-gson:2.9.0")


    }
}
dependencies {
    implementation(libs.firebase.auth)
    implementation(libs.androidx.camera.lifecycle)
    implementation(libs.androidx.camera.view)
    implementation(libs.firebase.firestore.ktx)
}

