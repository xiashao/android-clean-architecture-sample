package com.xiashao.plugin

import Versions
import com.android.build.gradle.LibraryExtension
import com.xiashao.extension.configureKtlint
import configureKotlinAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jlleitschuh.gradle.ktlint.KtlintExtension

class AndroidLibraryPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.android")
                apply("org.jetbrains.kotlin.kapt")
                apply("org.jlleitschuh.gradle.ktlint")
            }

            with(extensions) {
                configure<LibraryExtension> {
                    configureKotlinAndroid(this)
                    defaultConfig.targetSdk = Versions.TARGET_SDK
                }

                configure<KtlintExtension> {
                    configureKtlint(this)
                }
            }
        }
    }
}
