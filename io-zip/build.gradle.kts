@file:OptIn(ExperimentalKotlinGradlePluginApi::class)

import com.android.build.api.dsl.KotlinMultiplatformAndroidCompilation
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.android.kotlin.multiplatform.library)
    alias(libs.plugins.maven.publish)
}

kotlin {
    jvm {
        compilations.all {
            compileTaskProvider.configure {
                compilerOptions {
                    jvmTarget.set(JvmTarget.JVM_11)
                }
            }
        }
    }
    linuxX64()
    macosArm64()
    androidNativeArm64()
    androidNativeX64()
    android {
        namespace = "io.github.andannn.zip"
        compileSdk = 36

        withHostTest {
            isIncludeAndroidResources = true
        }
    }

    applyDefaultHierarchyTemplate {
        common {
            group("jvmAndAndroid") {
                withJvm()
                // https://issuetracker.google.com/issues/442950553
                withCompilations { it is KotlinMultiplatformAndroidCompilation }
            }
        }
    }

    sourceSets {
        all {
            languageSettings.optIn("kotlinx.cinterop.ExperimentalForeignApi")
        }
        commonMain.dependencies {
            implementation(libs.kotlinx.io)
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

mavenPublishing {
//    publishToMavenCentral()
//    signAllPublications()

    pom {
        name.set("kio-zip")
        description.set("Zip extension library using kotlinx-io.")
        url.set("https://github.com/andannn/RaylibKt")

        licenses {
            license {
                name.set("The Apache License, Version 2.0")
                url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                distribution.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
            }
        }

        developers {
            developer {
                id.set("andannn")
                name.set("Andannn")
            }
        }

        scm {
            url.set("https://github.com/andannn/kio-zip.git")
            connection.set("scm:git:git://github.com/andannn/kio-zip.git")
            developerConnection.set("scm:git:ssh://git@github.com/andannn/kio-zip.git")
        }
    }
}
