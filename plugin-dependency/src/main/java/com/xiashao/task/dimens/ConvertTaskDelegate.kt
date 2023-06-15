package com.xiashao.task.dimens

import com.android.build.gradle.internal.coverage.JacocoReportTask.JacocoReportWorkerAction.Companion.logger
import groovy.util.Node
import groovy.xml.XmlParser
import org.gradle.api.Project
import org.gradle.api.logging.LogLevel.DEBUG
import org.gradle.api.logging.LogLevel.INFO
import org.gradle.api.logging.LogLevel.WARN
import org.gradle.internal.xml.SimpleXmlWriter
import org.jetbrains.kotlin.com.google.common.io.Files
import java.io.File
import java.io.FileOutputStream
import java.text.DecimalFormat
import kotlin.math.abs

class ConvertTaskDelegate constructor(
    val project: Project,
    val baseSize: String? = null,
    var baseSizeModules: MutableList<String> = mutableListOf(),
    var baseSizePaths: MutableList<File> = mutableListOf(),
    var targetSize: String? = null,
    var useBreakPoint: String? = null,
    var copyBaseLayout: Boolean = false
) {
    companion object {
        private const val SIZE_DELIMITER = "*"
    }

    fun convert() {
        if (baseSize == null || targetSize == null) {
            logger.log(WARN, "both baseSize and targetSize are null.")
            return
        }
        val baseSizeDp = baseSize.split(SIZE_DELIMITER).map { it.toFloat() }
        val targetSizeDp = targetSize!!.split(SIZE_DELIMITER).map { it.toFloat() }

        val baseAspectRatio = baseSizeDp[0] / baseSizeDp[1]
        val targetAspectRatio = targetSizeDp[0] / targetSizeDp[1]
        val targetWindowSizeClass = calculateWindowSizeClass(targetSizeDp)

        if (targetWindowSizeClass.widthSizeClass.minValue == 0
            || targetWindowSizeClass.heightSizeClass.minValue == 0
        ) {
            logger.warn("default values exist.")
            return
        }
        logger.log(
            INFO,
            "baseAspectRatio: ${baseAspectRatio}\r\ntargetAspectRatio:$targetAspectRatio\r\ntargetWindowSizeClass:${targetWindowSizeClass}"
        )

        val srcFiles = mutableListOf<File>()
        val srcDimenModuleDirs = mutableListOf<String?>()
        if (baseSizeModules.isEmpty()) {
            baseSizeModules.add("") // current project
        }
        baseSizeModules.forEach { baseSizeModule ->
            val srcDimenModuleDir = getSrcDimenModuleDir(baseSizeModule)
            srcDimenModuleDirs.add(srcDimenModuleDir)
            val srcFile = File(
                srcDimenModuleDir,
                "src${File.separator}main${File.separator}res${File.separator}values${File.separator}dimens.xml"
            )
            srcFiles += srcFile
        }
        if (baseSizePaths.isNotEmpty()) {
            srcFiles.addAll(baseSizePaths)
        }
        val distFileDir = File(
            project.projectDir.absolutePath,
            "src${File.separator}main${File.separator}res${File.separator}values-${targetWindowSizeClass.getQualifier()}"
        )
        if (!distFileDir.exists()) {
            distFileDir.mkdirs()
        }
        val distFile = File(
            project.projectDir.absolutePath,
            "src${File.separator}main${File.separator}res${File.separator}values-${targetWindowSizeClass.getQualifier()}${File.separator}dimens.xml"
        )
        if (abs(baseAspectRatio - targetAspectRatio) > 0.01f) {
            logger.log(
                WARN, "baseAspectRatio($baseAspectRatio) is not equals to targetAspectRatio(${targetAspectRatio})."
            )
            return
        }
        val scaleFactor = targetSizeDp[0] / baseSizeDp[0]
        logger.log(
            INFO,
            "distFile :${distFile.absolutePath}\r\nscaleFactor:${scaleFactor}"
        )
        convertDimens(srcFiles, distFile, scaleFactor)
        if (copyBaseLayout) {
            copyLayout(srcDimenModuleDirs, targetWindowSizeClass)
        }
    }

    private fun getSrcDimenModuleDir(baseSizeModule: String?): String? {
        var srcFilePath = project.projectDir.absolutePath

        if (baseSizeModule?.isNotEmpty() == true) {
            srcFilePath = findProject(baseSizeModule)?.projectDir?.absolutePath
            logger.log(DEBUG, "srcFilePath:${srcFilePath}")
        }
        return srcFilePath
    }

    private fun calculateWindowSizeClass(targetSizeDp: List<Float>): WindowSizeClass {
        var targetWindowSizeClass = WindowSizeClass.calculateFromSize(
            targetSizeDp[0].toInt(),
            targetSizeDp[1].toInt()
        )
        if (useBreakPoint?.isNotEmpty() == true) {
            val breakPoint = useBreakPoint!!.split(SIZE_DELIMITER).map { it.toFloat() }
            targetWindowSizeClass = WindowSizeClass.customSize(
                breakPoint[0].toInt(),
                breakPoint[1].toInt()
            )
        }
        return targetWindowSizeClass
    }

    private fun findProject(path: String): Project? {
        return project.rootProject.subprojects.find { it.path == path }
    }

    private fun copyLayout(srcDimenModuleDirs: MutableList<String?>, targetWindowSizeClass: WindowSizeClass) {
        srcDimenModuleDirs.forEach { srcDimenModuleDir ->
            val srcLayoutDir = File(
                srcDimenModuleDir,
                "src${File.separator}main${File.separator}res${File.separator}layout"
            )
            val tartLayoutDir = File(
                project.projectDir.absolutePath,
                "src${File.separator}main${File.separator}res${File.separator}layout-${targetWindowSizeClass.getQualifier()}"
            )
            if (tartLayoutDir.exists()) {
                tartLayoutDir.delete()
            }
            tartLayoutDir.mkdirs()
            Files.copy(srcLayoutDir, tartLayoutDir)
        }
    }

    private fun convertDimens(srcFiles: MutableList<File>, distFile: File, scaleFactor: Float) {
        val dimens = mutableMapOf<String, String>()
        val xmlParser = XmlParser()
        val decimalFormat = DecimalFormat("0.00")
        srcFiles.filter { it.exists() }.forEach { srcFile ->
            val rootNode = xmlParser.parse(srcFile)
            val iterator = rootNode.depthFirst().iterator()
            while (iterator.hasNext()) {
                val next = iterator.next()
                if (next is Node && next.name().toString() == "dimen") {
                    val value = next.text() as String
                    val key = next.attribute("name") as String
                    val unit = value.substring(value.length - 2)
                    if (unit == "dp" || unit == "sp") {
                        val floatValue = value.substring(0, value.length - 2).toFloat()
                        dimens[key] = decimalFormat.format(floatValue * scaleFactor) + unit
                    } else {
                        dimens[key] = value
                    }
                }
            }
        }
        if (!distFile.exists()) {
            distFile.createNewFile()
        }
        val outputStream = FileOutputStream(distFile)
        val writer = SimpleXmlWriter(outputStream, "")
        val resourcesElement = writer.startElement("resources")
        dimens.forEach { d ->
            val dimenElement = resourcesElement.startElement("dimen")
            dimenElement.attribute("name", d.key)
            dimenElement.write(d.value)
            dimenElement.endElement()
        }
        resourcesElement.endElement()
        resourcesElement.flush()
        outputStream.close()
    }
}