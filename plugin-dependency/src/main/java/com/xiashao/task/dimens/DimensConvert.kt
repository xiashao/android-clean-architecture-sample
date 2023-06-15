package com.xiashao.task.dimens

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.TaskAction
import java.io.File

open class DimensConvert : DefaultTask() {

    /**
     * width(dp)* height(dp)
     * 1920*1080
     */
    @Input var baseSize: String? = null

    @Optional
    @Input var localModules: MutableList<String> = mutableListOf()

    @Optional
    @Input var localModulesPath: MutableList<File> = mutableListOf()

    /**
     * width(dp)* height(dp)
     * 2560*1440
     */
    @Input var targetSize: String? = null

    @Optional
    @Input var useBreakPoint: String? = null

    @Input var copyBaseLayout: Boolean = false

    @TaskAction
    fun convert() {
        ConvertTaskDelegate(
            project,
            baseSize,
            localModules,
            localModulesPath,
            targetSize,
            useBreakPoint,
            copyBaseLayout
        ).convert()
    }
}