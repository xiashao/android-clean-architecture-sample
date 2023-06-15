package com.xiashao.extension

import org.gradle.api.Project
import org.jlleitschuh.gradle.ktlint.KtlintExtension
import java.io.File

internal fun Project.configureKtlint(ktlintExtension: KtlintExtension) {

    ktlintExtension.apply {
        val lintDir = File(
            rootDir, "${File.separator}config${File.separator}quality${File.separator}ktlint"
        )
        additionalEditorconfigFile.set(
            File(
                lintDir.absolutePath, ".editorconfig"
            )
        )
    }
}
