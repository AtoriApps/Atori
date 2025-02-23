import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.compose.internal.com.squareup.kotlinpoet.FileSpec
import org.jetbrains.compose.internal.com.squareup.kotlinpoet.PropertySpec
import org.jetbrains.compose.internal.com.squareup.kotlinpoet.TypeSpec
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.text.SimpleDateFormat
import java.util.*

// 版本代码是yyyyMMdd动态生成
val verCode = SimpleDateFormat("yyyyMMdd").format(Date()).toInt()
// 当实现计划时记得撞♂版本号
val verName = "0.1.2"
// 包名
val appId = "app.atori"

val myGeneratedCodeDir = "generated/source/buildConstants"

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.ksp)
    alias(libs.plugins.room)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)
}

room {
    schemaDirectory("$projectDir/schemas")
}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_21)
        }
    }

    jvm("desktop")

    sourceSets {
        val desktopMain by getting

        val commonMain by getting {
            kotlin.srcDir("build/$myGeneratedCodeDir")
        }

        androidMain.dependencies {
            implementation(libs.androidx.activity.compose)
            implementation(libs.androidx.appcompat)
            implementation(libs.androidx.core.ktx)

            implementation(libs.smack.art)
        }

        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.material3)
            implementation(compose.components.resources)
            implementation(libs.compose.navigation)
            implementation(compose.components.uiToolingPreview)
            implementation(compose.preview)
            implementation(compose.uiTooling)

            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)

            implementation(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.koin.compose.viewmodel)
            implementation(libs.koin.compose.vmNavi)

            implementation(libs.androidx.room.runtime)
            implementation(libs.androidx.room.ktx)
            implementation(libs.androidx.sqlite.bundled)

            implementation(libs.androidx.datastore.preferences)

            implementation(libs.smack.tcp)
            implementation(libs.smack.im)
            implementation(libs.smack.extensions)
            implementation(libs.smack.experimental)
            implementation(libs.smack.omemo)
        }

        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutines.swing)

            implementation(libs.smack.jvm)
        }
    }
}

dependencies {
    add("kspAndroid", libs.androidx.room.compiler)
    add("kspDesktop", libs.androidx.room.compiler)
}

val generateBuildConstants = tasks.register("generateBuildConstants") {
    val outputDir = File(buildDir, myGeneratedCodeDir)
    val constClzName = "BuildConstants"

    outputs.dir(outputDir)

    doLast {
        // 创建 Kotlin 文件
        val buildConfigClass = TypeSpec.objectBuilder(constClzName)
            .addProperty(
                PropertySpec.builder("VERSION_NAME", String::class)
                    .initializer("%S", verName)
                    .build()
            ).addProperty(
                PropertySpec.builder("VERSION_CODE", Int::class)
                    .initializer("%L", verCode)
                    .build()
            )
            .build()

        // 生成文件
        val file = FileSpec.builder(appId, "BuildConstants")
            .addType(buildConfigClass)
            .build()

        outputDir.mkdirs()
        file.writeTo(outputDir)
    }
}

// 确保生成任务在编译时执行
tasks.named("assemble").configure {
    dependsOn(generateBuildConstants)
}

android {
    namespace = appId
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = appId
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = verCode
        versionName = verName
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            signingConfig = signingConfigs.getByName("debug")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    // Smack的xpp3冲突解决
    configurations {
        all {
            exclude(group = "xpp3", module = "xpp3")
            exclude(group = "xpp3", module = "xpp3_min")
        }
    }
}

compose.resources {
    publicResClass = false
    packageOfResClass = "$appId.resources"
    generateResClass = auto
}

// 打包会报错
compose.desktop {
    application {
        mainClass = "$appId.InitAppKt"

        nativeDistributions {
            targetFormats(TargetFormat.Exe)
            packageName = appId
            packageVersion = verName
        }
    }
}
