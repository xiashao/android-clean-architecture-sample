package com.xiashao.plugin

import com.google.protobuf.gradle.ProtobufExtension
import com.xiashao.extension.configureProtoBuf
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class ProtoBufPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.google.protobuf")
            }
            with(extensions) {
                configure<ProtobufExtension> {
                    configureProtoBuf(this)
                }
            }
        }
    }
}