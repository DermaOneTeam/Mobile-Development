plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.example.dermaone"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.dermaone"
        minSdk = 28
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    buildFeatures {
        viewBinding = true
        buildConfig = true
        dataBinding = true
    }
}

dependencies {

//    implementation("androidx.core:core-ktx:1.10.1")
//    implementation("androidx.appcompat:appcompat:1.6.1")
//    implementation("com.google.android.material:material:1.9.0")
//    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
//
//    implementation("androidx.datastore:datastore-preferences:1.0.0")
//    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1")
//    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.1")
//    implementation("androidx.activity:activity-ktx:1.7.2")
//
//    implementation("com.squareup.retrofit2:retrofit:2.9.0")
//    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
//    implementation("com.squareup.okhttp3:logging-interceptor:4.11.0")
//    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1") //untuk lifecycleScope
//
//    implementation("com.github.bumptech.glide:glide:4.13.1")
//    implementation("com.google.firebase:firebase-crashlytics-buildtools:3.0.0")
//    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.2.0-alpha01")
//    implementation("com.google.android.gms:play-services-maps:18.1.0")
//    implementation("androidx.paging:paging-common-android:3.3.0")
//    implementation("androidx.leanback:leanback-paging:1.1.0-alpha11")
//    implementation("androidx.paging:paging-runtime-ktx:3.3.0")
//    implementation("androidx.test.ext:junit-ktx:1.1.5")
//    implementation("androidx.room:room-ktx:2.6.1")
//
//    testImplementation("org.robolectric:robolectric:4.9.2")
//    testImplementation("junit:junit:4.13.2")
//    testImplementation("org.mockito:mockito-core:3.11.2")
//    testImplementation("org.mockito:mockito-inline:3.11.2")


    // here
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.datastore:datastore-core-android:1.1.1")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    // button border
    implementation("androidx.appcompat:appcompat:1.1.0")
    implementation("com.google.android.material:material:1.0.0")

    // glide
    implementation("com.github.bumptech.glide:glide:4.11.0")

    // bottom navigation menu
    implementation("com.google.android.material:material:1.3.0-alpha03")

    // api
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:okhttp:4.11.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.11.0")

    // lifecycle
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")

    // datastore
    implementation("androidx.datastore:datastore-preferences:1.0.0")

    // viewmodels
    implementation("androidx.activity:activity-ktx:1.7.2")

    // gson
    implementation("com.google.code.gson:gson:2.8.5")
}