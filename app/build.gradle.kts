plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.realme.otc"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.realme.otc"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions { jvmTarget = "17" }
}

configurations.all {
    resolutionStrategy {
        force("com.github.pedroSG94.RootEncoder:library:2.5.5")
        force("com.github.pedroSG94.RootEncoder:common:2.5.5")
        force("com.github.pedroSG94.RootEncoder:rtsp:2.5.5")
        force("com.github.pedroSG94.RootEncoder:rtmp:2.5.5")
        force("com.github.pedroSG94.RootEncoder:srt:2.5.5")
        force("com.github.pedroSG94.RootEncoder:udp:2.5.5")
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")

    implementation("com.github.pedroSG94.RootEncoder:library:2.5.5")
    implementation("com.github.pedroSG94:RTSP-Server:1.4.2")

    implementation("androidx.media3:media3-exoplayer:1.3.1")
    implementation("androidx.media3:media3-exoplayer-rtsp:1.3.1")
    implementation("androidx.media3:media3-ui:1.3.1")
}
