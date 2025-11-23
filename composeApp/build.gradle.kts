import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.composeHotReload)
}

// Dynamic versioning support for semantic-release
val appVersion: String = (findProperty("version") as? String) ?: "1.0.0"

// Calculate versionCode from semantic version supporting XXX.YYY.ZZZ format
// (e.g., 1.2.3 -> 1002003, 12.34.567 -> 12034567)
val appVersionCode: Int = appVersion.split(".")
    .map { it.toIntOrNull() ?: 0 }
    .let { parts ->
        val major = parts.getOrNull(0) ?: 0
        val minor = parts.getOrNull(1) ?: 0
        val patch = parts.getOrNull(2) ?: 0
        major * 1000000 + minor * 1000 + patch
    }

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
    
    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }
    
    jvm()
    
    sourceSets {
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.lifecycle.runtimeCompose)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.kotest.assertions)
            implementation(libs.turbine)
            implementation(libs.kotlinx.coroutines.test)
        }

        // MockK only supports JVM/Android, not iOS/Native
        jvmTest.dependencies {
            implementation(libs.mockk)
        }

        androidInstrumentedTest.dependencies {
            implementation(libs.mockk)
        }
        jvmMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutinesSwing)
        }
    }
}

android {
    namespace = "engineer.gus.segnale"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "engineer.gus.segnale"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = appVersionCode
        versionName = appVersion
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "/META-INF/LICENSE.md"
            excludes += "/META-INF/LICENSE-notice.md"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    debugImplementation(compose.uiTooling)

    // Compose UI Testing (Android)
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    // Compose-specific lint checks
    lintChecks(libs.compose.lints)
}

compose.desktop {
    application {
        mainClass = "engineer.gus.segnale.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "engineer.gus.segnale"
            packageVersion = appVersion
        }
    }
}
