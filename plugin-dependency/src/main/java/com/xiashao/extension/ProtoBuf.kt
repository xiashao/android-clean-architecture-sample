package com.xiashao.extension

import com.google.protobuf.gradle.ProtobufExtension
import org.gradle.api.Project

internal fun Project.configureProtoBuf(protobufExtension: ProtobufExtension) {
    protobufExtension.protoc {
        artifact = "com.google.protobuf:protoc:3.14.0"
    }
    protobufExtension.generateProtoTasks {
        all().forEach { task ->
            task.builtins.create("java").option("lite")
        }
    }
}