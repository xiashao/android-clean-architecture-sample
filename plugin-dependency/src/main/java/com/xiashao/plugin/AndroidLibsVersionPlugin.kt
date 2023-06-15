package com.xiashao.plugin

import com.xiashao.extension.configureKtlint
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jlleitschuh.gradle.ktlint.KtlintExtension

class AndroidLibsVersionPlugin : Plugin<Project> {
    override fun apply(target: Project) {

        with(target) {
            with(pluginManager) {
                apply("org.jlleitschuh.gradle.ktlint")
            }
            with(extensions) {
                configure<KtlintExtension> {
                    configureKtlint(this)
                }
            }
        }
    }
}