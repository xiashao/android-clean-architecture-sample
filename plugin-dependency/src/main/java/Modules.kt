object FoundationModules {

    object TechLibs {
        const val LOG = ":TechLibs:log"
        const val NETWORK = ":TechLibs:network"
        const val IMAGE = ":TechLibs:image"
        const val Utils = ":TechLibs:utils"
        const val MODEL = ":TechLibs:model"
    }

    object AppLibs {
        const val UI = ":AppLibs:ui"
        const val DOMAIN = ":AppLibs:domain"
        const val DI = ":AppLibs:di"
    }

    object UILibs {
        const val VIEW = ":UILibs:view"
        const val THEME = ":UILibs:theme"
    }

    object Services {
        object Location {
            const val SDK = ":Services:Location:sdk"
            const val MODEL = ":Services:Location:shared"
            const val PROVIDER = ":Services:Location:provider"
        }
        object Car {
            const val UMS = ":Services:Car:ums"
            const val SDK = ":Services:Car:sdk"
        }
        object Voice {
            const val SDK = ":Services:Voice:sdk"
            const val MODEL = ":Services:Voice:model"
            const val TTS = ":Services:Voice:tts"
            const val ASSISTANT = ":Services:Voice:assistant"
        }
        object Analysis {
            const val SDK = ":Services:Analysis:sdk"
        }
        object Factory {
            const val TEST = ":Services:FactoryTest:pointInspector"
        }
    }
}

fun String.toArtifactId(): String {
    return replace(":", "-").removePrefix("-")
}

