import com.android.build.api.dsl.CommonExtension
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.api.plugins.ExtensionAware
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions
import java.io.File
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.concurrent.TimeUnit.MINUTES
import java.util.concurrent.TimeUnit.SECONDS

/**
 * Configure base Kotlin with Android options
 */
internal fun Project.configureKotlinAndroid(
    commonExtension: CommonExtension<*, *, *, *>,
) {
    commonExtension.apply {

        compileSdk = Versions.COMPILE_SDK

        defaultConfig {
            minSdk = Versions.MIN_SDK
            buildToolsVersion = Versions.BUILD_TOOLS
        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_17
            targetCompatibility = JavaVersion.VERSION_17
            isCoreLibraryDesugaringEnabled = true
        }

        kotlinOptions {
            // Treat all Kotlin warnings as errors (disabled by default)
            allWarningsAsErrors = properties["warningsAsErrors"] as? Boolean ?: false

            freeCompilerArgs = freeCompilerArgs + listOf(
                "-opt-in=kotlin.RequiresOptIn",
                // Enable experimental coroutines APIs, including Flow
                "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
                "-opt-in=kotlinx.coroutines.FlowPreview",
                "-opt-in=kotlin.Experimental",
                // Enable experimental kotlinx serialization APIs
                "-opt-in=kotlinx.serialization.ExperimentalSerializationApi"
            )

            // Set JVM target to 1.8
            jvmTarget = JavaVersion.VERSION_17.toString()
        }
        buildTypes {
            getByName("release") {
                isMinifyEnabled = false
                proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
                )
            }
        }

        lint {
            disable += mutableSetOf(
                "TypographyFractions",
                "TypographyQuotes",
                "RtlHardcoded",
                "RtlCompat",
                "RtlEnabled", "Instantiatable"
            )
            // If set to true, turns off analysis progress reporting by lint.
            quiet = true
            // If set to true (default), stops the build if errors are found.
            abortOnError = false
            // If true, only report errors.
            ignoreWarnings = true
            // If true, lint also checks all dependencies as part of its analysis. Recommended for
            // projects consisting of an app with library dependencies.
            checkDependencies = true
            val lintDir = File(
                rootDir, "${File.separator}config${File.separator}quality${File.separator}lint"
            )
            lintConfig = File(
                lintDir.absolutePath, "lint.xml"
            )
        }

        testOptions {
            unitTests {
                isIncludeAndroidResources = true
            }
        }

        configurations.all {
            resolutionStrategy.cacheDynamicVersionsFor(5, MINUTES)
            resolutionStrategy.cacheChangingModulesFor(60, SECONDS)
        }
    }


    dependencies {
        add("coreLibraryDesugaring", Libs.CORELIBRARY_DESUGARING)
        add("api", Libs.ANNOTATION)
        addUnitTestDependencies()
    }
    configurations.configureEach {
        resolutionStrategy {
            force(Libs.JUNIT4)
        }
    }
}

internal fun DependencyHandler.addUnitTestDependencies() {
    add("testImplementation", Libs.JUNIT4)
    add("testImplementation", Libs.ANDROIDX_TEST_CORE)
    add("testImplementation", Libs.COROUTINES_TEST)
    add("testImplementation", Libs.MOCKITO)
    add("testImplementation", Libs.MOCKITO_KOTLIN)
    add("testImplementation", Libs.ANDROIDX_JUNIT)
    add("testImplementation", Libs.TRUTH)
    add("testImplementation", Libs.ROBOLECTRIC)

    add("androidTestImplementation", Libs.ANDROID_JUNIT_EXT)
    add("androidTestImplementation", Libs.ANDROIDX_JUNIT)
    add("androidTestImplementation", Libs.ESPRESSO_CORE)
    add("androidTestImplementation", Libs.TRUTH)
}

fun CommonExtension<*, *, *, *>.kotlinOptions(block: KotlinJvmOptions.() -> Unit) {
    (this as ExtensionAware).extensions.configure("kotlinOptions", block)
}

fun BaseAppModuleExtension.setApkNamePrefix(
    prefix: String
) {
    val date: String = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"))
    applicationVariants.all {
        val buildType = buildType.name
        outputs.all {
            if (this is com.android.build.gradle.internal.api.ApkVariantOutputImpl) {
                outputFileName =
                    "$prefix-${buildType}-${versionName}-${date}.apk"
            }
        }
    }
}