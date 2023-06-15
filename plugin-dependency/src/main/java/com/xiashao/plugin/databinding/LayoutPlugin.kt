package com.xiashao.plugin.databinding

import com.android.build.gradle.AppExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

class LayoutPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        val appExtension = project.extensions.getByType(
            AppExtension::class.java
        )
        appExtension.registerTransform(LayoutTransform(project))
    }
}