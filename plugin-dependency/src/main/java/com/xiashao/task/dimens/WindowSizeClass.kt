package com.xiashao.task.dimens

data class WindowSizeClass constructor(
    val widthSizeClass: WindowWidthSizeClass, val heightSizeClass: WindowHeightSizeClass
) {
    companion object {

        @JvmStatic
        fun calculateFromSize(width: Int, height: Int): WindowSizeClass {
            val windowWidthSizeClass = WindowWidthSizeClass.fromWidth(width)
            val windowHeightSizeClass = WindowHeightSizeClass.fromHeight(height)
            return WindowSizeClass(windowWidthSizeClass, windowHeightSizeClass)
        }

        @JvmStatic
        fun customSize(width: Int, height: Int): WindowSizeClass {
            val windowWidthSizeClass = WindowWidthSizeClass(width)
            val windowHeightSizeClass = WindowHeightSizeClass(height)
            return WindowSizeClass(windowWidthSizeClass, windowHeightSizeClass)
        }
    }

    fun getQualifier(): String {
        return "w${widthSizeClass.minValue}dp-h${heightSizeClass.minValue}dp"
    }
}

data class WindowWidthSizeClass constructor(
    val minValue: Int
) {
    companion object {
        val Compact = WindowWidthSizeClass(0)
        val Medium = WindowWidthSizeClass(600)
        val Large = WindowWidthSizeClass(840)
        val Expanded = WindowWidthSizeClass(1400)

        @JvmStatic
        fun fromWidth(widthDp: Int): WindowWidthSizeClass {
            return when {
                widthDp < Medium.minValue -> Compact
                widthDp < Large.minValue -> Medium
                widthDp < Expanded.minValue -> Large
                else -> Expanded
            }
        }
    }
}

data class WindowHeightSizeClass constructor(
    val minValue: Int
) {
    companion object {
        val Compact = WindowHeightSizeClass(0)
        val Medium = WindowHeightSizeClass(480)
        val Expanded = WindowHeightSizeClass(900)

        @JvmStatic
        fun fromHeight(heightDp: Int): WindowHeightSizeClass {
            return when {
                heightDp < Medium.minValue -> Compact
                heightDp < Expanded.minValue -> Medium
                else -> Expanded
            }
        }
    }
}

