package com.xiashao.plugin

import Libs
import Versions
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import com.xiashao.extension.configureKtlint
import configureKotlinAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.jlleitschuh.gradle.ktlint.KtlintExtension
import java.io.File

class AndroidApplicationPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")
                apply("org.jetbrains.kotlin.kapt")
                apply("dagger.hilt.android.plugin")
                apply("org.jlleitschuh.gradle.ktlint")
            }

            with(extensions) {
                configure<BaseAppModuleExtension> {
                    configureKotlinAndroid(this)

                    val major = findProperty("major") ?: "1"
                    val minor = findProperty("minor") ?: "0"
                    val buildNumber = findProperty("buildNumber") ?: "0"
                    val appVersionName = "${major}.${minor}.$buildNumber"
                    val appVersionCode =
                        "${major}${minor.toString().padStart(2, '0')}${buildNumber.toString().padStart(6, '0')}"
                    defaultConfig.versionName = appVersionName
                    defaultConfig.versionCode = appVersionCode.toInt()
                    defaultConfig.targetSdk = Versions.TARGET_SDK

                    buildFeatures {
                        viewBinding = true
                        dataBinding = true
                    }

                    signingConfigs {
                        create("vivid") {
                            keyAlias = "arp_keystore"
                            keyPassword = "xiashao"
                            storePassword = "xiashao"
                            storeFile = File(
                                rootDir,
                                "${File.separator}config/${File.separator}keystore${File.separator}vivid_keystore.jks"
                            )
                            enableV1Signing = true
                            enableV2Signing = true
                        }
                    }
                    buildTypes {
                        release {
                            isMinifyEnabled = true
                            signingConfig = signingConfigs.getByName("vivid")

                            proguardFiles(
                                getDefaultProguardFile("proguard-android-optimize.txt"),
                                "proguard-rules.pro",
                                File(
                                    rootDir,
                                    "${File.separator}config/${File.separator}proguard${File.separator}proguard-rules.pro"
                                )
                            )
                        }
                    }
                }

                configure<KtlintExtension> {
                    configureKtlint(this)
                }
            }

            dependencies {
                add("implementation", Libs.HILT_ANDROID)
                add("kapt", Libs.HILT_COMPILER)
            }
        }
    }
}
