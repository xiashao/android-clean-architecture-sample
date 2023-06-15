package com.xiashao.plugin.dimensconvert

import org.gradle.api.Project
import org.gradle.api.artifacts.Configuration
import org.gradle.api.artifacts.DependencyResolutionListener
import org.gradle.api.artifacts.ResolvableDependencies
import org.gradle.api.internal.artifacts.dependencies.DefaultProjectDependency

class ConfigResolutionListener constructor(
    private val project: Project,
    private val configuration: Configuration
) :
    DependencyResolutionListener {

    override fun beforeResolve(dependencies: ResolvableDependencies) {
        configuration.dependencies.forEach { dependency ->
            if (dependency is DefaultProjectDependency) {
                if (dependency.targetConfiguration == null) {
                    dependency.targetConfiguration = "default"
                }
                val copyDependency = dependency.copy()
                copyDependency.targetConfiguration = null
                project.dependencies.add("implementation", copyDependency)
            } else {
                project.dependencies.add("implementation", dependency)
            }
        }
        project.gradle.removeListener(this)
    }

    override fun afterResolve(dependencies: ResolvableDependencies) {
    }
}