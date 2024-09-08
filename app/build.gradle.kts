plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("com.google.gms.google-services")
    // Plugins for safe arguments to pass data between one fragment to another
    id("androidx.navigation.safeargs.kotlin")
    id("kotlin-parcelize")   // for parcelize --> for making data class parcelable
}

android {
    namespace = "com.fps69.abworkmanager"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.fps69.abworkmanager"
        minSdk = 26
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
    buildFeatures{
        viewBinding =true
    }

    packagingOptions {
        exclude("META-INF/DEPENDENCIES")
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.firebase.messaging)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // dependencies For Firebase
    implementation("com.google.firebase:firebase-bom:33.1.2")
    implementation ("com.google.firebase:firebase-auth-ktx:21.0.7")
    // Firebase Storage
    implementation("com.google.firebase:firebase-storage:21.0.0")
    // Firebase Realtime Database
    implementation("com.google.firebase:firebase-database:21.0.0")


    // This is dependencies for kotlin coroutines
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.7.2")


    //Glide
    implementation ("com.github.bumptech.glide:glide:4.16.0")
    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    // Retrofit Gson Converter
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // Circle Image
    implementation ("de.hdodenhof:circleimageview:3.1.0")

    // Text Dimension
    implementation ("com.intuit.ssp:ssp-android:1.1.1")
    implementation ("com.intuit.sdp:sdp-android:1.1.1")


    // For Firebase messaging
    implementation("com.google.firebase:firebase-messaging-ktx:24.0.1")



    implementation("com.google.auth:google-auth-library-oauth2-http:1.3.0")



}