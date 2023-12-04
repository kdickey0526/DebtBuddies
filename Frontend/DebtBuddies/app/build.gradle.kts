import org.gradle.api.tasks.javadoc.Javadoc
import org.gradle.external.javadoc.StandardJavadocDocletOptions

//import org.gradle.api.tasks.javadoc.Javadoc
import org.gradle.api.tasks.SourceSetContainer
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.withType

plugins {
    id("com.android.application")
}

//val javadocTaskName = "generateReleaseJavadoc"

//tasks.register<Javadoc>(javadocTaskName) {
//    group = "documentation"
//    description = "Generates Javadoc for the release."
//
//    val sourceSets = the<SourceSetContainer>()
//    source = sourceSets["main"].allJava
//    classpath = project.configurations["runtimeClasspath"]
////    destinationDir = project.file("build/docs/javadoc")
//
//    (options as org.gradle.external.javadoc.StandardJavadocDocletOptions).apply {
//        addStringOption("Xdoclint:none", "-quiet")
//        memberLevel = org.gradle.external.javadoc.JavadocMemberLevel.PUBLIC
//        link("https://docs.oracle.com/javase/8/docs/api/")
//    }
//}

android {
    compileSdkVersion 33

    namespace = "com.example.debtbuddies"

    defaultConfig {
        applicationId = "com.example.debtbuddies"
        minSdk = 33
        targetSdk = 33
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
    buildFeatures {
        viewBinding = true
    }

//    tasks {
//        // Other task configurations...
//
//        // Add the Javadoc generation task
//        register("generateReleaseJavadoc", Javadoc::class) {
//            group = "documentation"
//            description = "Generates Javadoc for the release."
//
//            // Set the source files for Javadoc generation
//            source = sourceSets["main"].allJava
//            // Define the classpath to include the dependencies
//            classpath = configurations["runtimeClasspath"]
//            // Set the destination directory for the generated Javadoc
//            destinationDir = file("build/docs/javadoc")
//
//            // Configure the Javadoc options
//            options {
//                this as StandardJavadocDocletOptions
//                addStringOption("Xdoclint:none", "-quiet")
//                memberLevel = JavadocMemberLevel.PUBLIC
//                links("https://docs.oracle.com/javase/8/docs/api/")
//            }
//        }
//    }

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
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation ("androidx.cardview:cardview:1.0.0")
}
