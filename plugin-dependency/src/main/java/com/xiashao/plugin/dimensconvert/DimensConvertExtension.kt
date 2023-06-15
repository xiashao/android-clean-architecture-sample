package com.xiashao.plugin.dimensconvert

abstract class DimensConvertExtension {
    companion object {
        const val NAME = "dimensConvert"
    }

    var baseSize: String? = null
    var localModules: MutableList<String> = mutableListOf()
    var targetSize: String? = null
    var useBreakPoint: String? = null
    var copyBaseLayout: Boolean = false
}