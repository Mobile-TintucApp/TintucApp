    plugins {
        alias(libs.plugins.android.application)
        alias(libs.plugins.google.gms.google.services)
    }
    
    android {
        namespace = "nhom4Mobile.ueh.edu.tintucapp"
        compileSdk = 34
    
        defaultConfig {
            applicationId = "nhom4Mobile.ueh.edu.tintucapp"
            minSdk = 29
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
    }
    
    dependencies {
    
        implementation(libs.appcompat)
        implementation(libs.material)
        implementation(libs.activity)
        implementation(libs.constraintlayout)
        implementation(libs.firebase.firestore)
        testImplementation(libs.junit)
        androidTestImplementation(libs.ext.junit)
        androidTestImplementation(libs.espresso.core)
        //Xác thực người dùng
        implementation("com.google.firebase:firebase-auth")
        // CSDL
        implementation("com.google.firebase:firebase-firestore")
        implementation("com.google.firebase:firebase-database:19.6.0")
        //Thông báo đẩy
        implementation("com.google.firebase:firebase-messaging")
        //Điều hướng + Back
        implementation("androidx.navigation:navigation-fragment:2.7.2")
        implementation("androidx.navigation:navigation-ui:2.7.2")
        //ảnh internet
        implementation("com.squareup.picasso:picasso:2.8")

        // Thêm Glide
        implementation("com.github.bumptech.glide:glide:4.12.0")
        annotationProcessor("com.github.bumptech.glide:compiler:4.12.0")
    }