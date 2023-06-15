package com.xiashao.plugin.dimensconvert

import org.gradle.api.Project
import org.gradle.api.artifacts.ResolvedArtifact
import java.io.File

data class ExplodedAar(val project: Project, val artifact: ResolvedArtifact) {

    fun getRootFolder(): File {
        val explodedRootDir =
            project.file("${project.buildDir}${File.separator}intermediates${File.separator}exploded-aar${File.separator}")
        val id = artifact.moduleVersion.id
        return project.file(
            explodedRootDir.path.plus(File.separator)
                .plus(id.group).plus(File.separator)
                .plus(id.name).plus(File.separator)
                .plus(id.version)

        )
    }

    fun getAssetsFolder(): File {
        return File(getRootFolder(), "assets")
    }

    fun getResFolder(): File {
        return File(getRootFolder(), "res")
    }

    fun getValuesFolder(): File {
        return File(getResFolder(), "values")
    }

    fun getValuesXml(): File {
        return File(getValuesFolder(), "values.xml")
    }
}