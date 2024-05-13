plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
    id ("kotlin-parcelize")

}

android {
    namespace = "com.easy_tiffin"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.easy_tiffin"
        minSdk = 26
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
        // freeCompilerArgs = ['-Xjvm-default=enable']
    }
    buildFeatures {
        compose = true
        viewBinding = true
        dataBinding =true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
//    dataBinding {
//        enabled = true
//    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.activity:activity-compose:1.7.2")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.test:core-ktx:1.5.0")
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.3")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.3")

    //firebase
    implementation("com.google.firebase:firebase-analytics:21.5.0")
    implementation("com.google.firebase:firebase-auth:22.3.0")
    implementation("com.google.firebase:firebase-firestore:24.10.0")
    implementation(platform("com.google.firebase:firebase-bom:32.7.0"))

    implementation ("com.google.android.libraries.places:places:2.5.0")

    ////

    implementation("com.github.leandroborgesferreira:loading-button-android:2.3.0")
    implementation("com.google.firebase:firebase-messaging:23.4.0")
    implementation("com.google.firebase:firebase-crashlytics:18.6.1")
    implementation("com.google.firebase:firebase-functions-ktx:20.4.0")
    implementation("androidx.activity:activity:1.8.0")

    testImplementation ("androidx.arch.core:core-testing:2.1.0")
    implementation("androidx.annotation:annotation:1.7.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
    testImplementation ("io.mockk:mockk:1.12.0")
    testImplementation("org.mockito:mockito-all:1.8.4")
    testImplementation ("org.mockito:mockito-core:2.24.5")
    testImplementation ("org.mockito:mockito-inline:2.24.5")


    //stripe
    implementation ("com.stripe:stripe-android:20.37.1")







//    val dagger_version = "2.41"
//    implementation("com.google.dagger:dagger:$dagger_version")
//    kapt("com.google.dagger:dagger-compiler:$dagger_version")
    implementation("com.google.dagger:hilt-android:2.44")
    kapt("com.google.dagger:hilt-android-compiler:2.44")

    val lifecycle_version = "2.5.0-alpha03"
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle_version")

    val retrofit_version = "2.9.0"
    implementation("com.squareup.retrofit2:retrofit:$retrofit_version")
    implementation("com.squareup.retrofit2:converter-gson:$retrofit_version")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.3")

    val room_version = "2.4.2"
    implementation("androidx.room:room-runtime:$room_version")
    implementation("androidx.room:room-ktx:$room_version")
    kapt("androidx.room:room-compiler:$room_version")
    implementation("androidx.room:room-rxjava2:$room_version")


    val coroutines_version = "1.6.0"
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutines_version")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutines_version")

    //Country picker
    implementation ("com.hbb20:ccp:2.5.1")

    implementation ("com.github.ozcanalasalvar:otpview:2.0.1")

    //For view based UI's
    implementation ("androidx.compose.material3:material3:Tag")

    //Facebook
    implementation ("com.facebook.android:facebook-login:latest.release")

//unit Testing
    testImplementation ("junit:junit:4.13.2")
    testImplementation ("org.mockito:mockito-core:5.10.0")
    testImplementation ("org.mockito.kotlin:mockito-kotlin:4.0.0") // use the latest version

    testImplementation ("org.mockito:mockito-inline:4.5.1")
    testImplementation ("androidx.arch.core:core-testing:2.1.0")
    testImplementation ("org.robolectric:robolectric:4.11.1") // use the latest version
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4")  // Or the latest version



}