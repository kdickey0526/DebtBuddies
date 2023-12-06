import org.gradle.api.tasks.javadoc.Javadoc
import org.gradle.external.javadoc.StandardJavadocDocletOptions

//import org.gradle.api.tasks.javadoc.Javadoc
import org.gradle.api.tasks.SourceSetContainer
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.withType




plugins {
    id("com.android.application")
}


android {
    namespace = "com.example.debtbuddies"
    compileSdk = 33

//    testOptions {
//        unitTests.includeAndroidResources = true
//
//    }

    defaultConfig {
        applicationId = "com.example.debtbuddies"
        minSdk = 33
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }


    buildTypes {
        debug {
            enableAndroidTestCoverage = true // maybe this is the right variable...?
        }

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
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation ("org.java-websocket:Java-WebSocket:1.5.1")
    implementation(platform("org.jetbrains.kotlin:kotlin-bom:1.8.0"))
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.annotation:annotation:1.6.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1")
    implementation("com.android.volley:volley:1.2.1")
    implementation("androidx.navigation:navigation-fragment:2.5.3")
    implementation("androidx.navigation:navigation-ui:2.5.3")

    // the dependency directly below breaks the code and stops it from running lol
//    implementation(fileTree(mapOf(
//        "dir" to "C:\\Users\\kdick\\AppData\\Local\\Android\\Sdk\\platforms\\android-33",
//        "include" to listOf("*.aar", "*.jar")
//        "exclude" to listOf()
//    )))
    implementation("com.google.android.gms:play-services-tflite-acceleration-service:16.0.0-beta01")
    implementation("com.google.ar.sceneform:filament-android:1.17.1")
    implementation("androidx.leanback:leanback:1.0.0")
    implementation("androidx.test:runner:1.5.2")
//    implementation("androidx.test:runner:1.5.2")

    testImplementation("junit:junit:4.13.2")
    implementation("androidx.test.ext:junit:1.1.5")
    testDebugImplementation("androidx.test.espresso:espresso-core:3.5.1")
//    implementation ("com.android.volley:volley:1.1.1")    // duplicate entry
    implementation ("androidx.cardview:cardview:1.0.0")


    implementation ("androidx.appcompat:appcompat:1.1.0")
    implementation ("androidx.constraintlayout:constraintlayout:1.1.3")
//    implementation ("junit:junit:4.13")
    // required if you want to use Mockito for unit tests
    testImplementation ("org.mockito:mockito-core:4.0.0")
//    implementation ("org.robolectric:robolectric:4.3.1")
    testImplementation ("androidx.test:core:1.2.0")


    // required if you want to use Mockito for Android tests
//    implementation ("org.mockito:mockito-android:2.7.22")
    implementation("com.android.support.test:rules:1.0.2")
    implementation ("com.android.support.test:runner:1.0.2")
//    androidTestImplementation 'androidx.test.ext:junit:1.1.1'
//    androidTestImplementation 'androidx.test.espresso:espresso-core:3.2.0'
    implementation ("com.android.volley:volley:1.1.1")
    implementation ("androidx.test.ext:junit:1.1.3")
    implementation("androidx.test.espresso:espresso-contrib:3.4.0")


}