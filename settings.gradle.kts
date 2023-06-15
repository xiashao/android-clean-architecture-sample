fun configModules(
    dir: File, isModuleNameAddProjectName: Boolean = false
) {
    println(dir.absolutePath)
    dir.listFiles()?.forEach { f: File ->
        findModules(f, "").forEach() {
            if (!isModuleNameAddProjectName) {
                println("Module Name --> ${it.key} \nModule Path: ${it.value}")
                include(it.key)
                project(it.key).projectDir = File(it.value)
            } else {
                println("Module Name --> :${dir.name}${it.key} \nModule Path: ${it.value}")
                include(":${dir.name}${it.key}")
                project(":${dir.name}${it.key}").projectDir = File(it.value)
            }
        }
    }
}

fun findModules(f: File, moduleName: String): Map<String, String> {
    val result = mutableMapOf<String, String>()
    if (isModule(f)) {
        moduleName.takeIf { moduleName != "" }
            ?.run {
                result["${moduleName}:${f.name}"] = f.absolutePath
            }
            ?: run {
                result[":${f.name}"] = f.absolutePath
            }
    } else {
        f.listFiles()?.forEach {
            result.putAll(findModules(it, if (moduleName == "") ":${f.name}" else "${moduleName}:${f.name}"))
        }
    }
    return result
}

fun isModule(it: File): Boolean {
    val moduleSymbol = arrayOf("build.gradle.kts")
    if (it.isDirectory) {
        val ignoreDirs = arrayOf(".gradle", ".idea", ".git", "gradle", "build", ".DS_Store")
        if (ignoreDirs.contains(it.name)) return false
        it.listFiles()
            .takeIf { fs -> fs.any { !it.isDirectory && moduleSymbol.contains(it.name) } }
            ?.let { arrary ->
                return true
            }
    }
    return false
}

pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "android-clean-architecture-sample"
include(":app")
include(":Domain:core-usecase")

include(":Data:core-data")
include(":Data:core-model")
includeBuild("plugin-dependency")