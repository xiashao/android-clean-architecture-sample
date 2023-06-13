package com.xiashao.model

import com.xiashao.model.DataSourceType.NETWORK

enum class DataSourceType {
    NETWORK, DB, DS;

    fun next(): DataSourceType {
        val all = values()
        return all[(ordinal + 1) % all.size]
    }
}

fun String?.asDataSourceType() = when (this) {
    null -> NETWORK
    else -> DataSourceType.values()
        .firstOrNull { type -> type.name == this }
        ?: NETWORK
}
