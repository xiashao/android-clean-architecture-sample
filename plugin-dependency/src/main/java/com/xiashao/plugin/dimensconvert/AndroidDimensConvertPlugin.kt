package com.xiashao.plugin.dimensconvert

import com.xiashao.task.dimens.ConvertTaskDelegate
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.Configuration
import org.gradle.api.artifacts.ResolvedDependency
import org.gradle.kotlin.dsl.support.unzipTo

class AndroidDimensConvertPlugin : Plugin<Project> {

    companion object {
        const val ARTIFACT_TYPE_AAR = "aar"
        const val CONFIG_NAME = "implResolved"
    }

    private lateinit var project: Project
    private val resolvedConfigurations = mutableListOf<Configuration>()

    override fun apply(target: Project) {
        this.project = target
        createExtension()
        createConfigurations()
        project.afterEvaluate {
            doAfterEvaluate()
        }
    }

    private fun createExtension() {
        project.extensions.create(DimensConvertExtension.NAME, DimensConvertExtension::class.java)
    }

    private fun createConfigurations() {
        val config = project.configurations.create(CONFIG_NAME)
        buildConfiguration(config)
    }

    private fun buildConfiguration(configuration: Configuration) {
        configuration.isVisible = false
        configuration.isTransitive = false
        project.gradle.addListener(ConfigResolutionListener(project, configuration))
        resolvedConfigurations.add(configuration)
    }

    private fun doAfterEvaluate() {
        val aarArtifacts = mutableListOf<ExplodedAar>()
        resolvedConfigurations.forEach { config ->
            if (config.name == CONFIG_NAME) {
                val firstLevelDependencies = mutableListOf<ResolvedDependency>()
                val resolveArtifacts = resolveAarArtifacts(config)
                aarArtifacts.addAll(resolveArtifacts)
                firstLevelDependencies.addAll(config.resolvedConfiguration.firstLevelModuleDependencies)
            }
        }

        aarArtifacts.forEach { aar ->
            val zipFolder = aar.getRootFolder()
            if (zipFolder.exists()) {
                zipFolder.delete()
            }
            zipFolder.mkdirs()
            unzipTo(zipFolder, aar.artifact.file)
        }

        val ext = project.extensions.getByType(DimensConvertExtension::class.java)
        ConvertTaskDelegate(
            project = project,
            baseSize = ext.baseSize,
            targetSize = ext.targetSize,
            useBreakPoint = ext.useBreakPoint,
            baseSizeModules = ext.localModules,
            copyBaseLayout = ext.copyBaseLayout,
            baseSizePaths = aarArtifacts.map { it.getValuesXml() }.toMutableList()
        ).convert()
    }

    private fun resolveAarArtifacts(configuration: Configuration): MutableList<ExplodedAar> {
        val list = mutableListOf<ExplodedAar>()
        configuration.resolvedConfiguration.resolvedArtifacts.forEach { artifact ->
            if (artifact.type == ARTIFACT_TYPE_AAR) {
                list.add(ExplodedAar(project, artifact))
            }
        }
        return list
    }
}