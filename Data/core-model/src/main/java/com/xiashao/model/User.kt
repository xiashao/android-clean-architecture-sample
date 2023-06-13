package com.xiashao.model

import com.xiashao.model.DataSourceType.NETWORK

data class User(
    val avatar: String,
    val email: String,
    val firstName: String,
    val id: Int,
    val lastName: String,
    val dataSourceType: DataSourceType? = NETWORK
) {
    fun fullName() = "$id -[$firstName $lastName]"
}
