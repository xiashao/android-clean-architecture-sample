package com.xiashao.data.database.mapper

import com.xiashao.data.database.entity.UserEntity
import com.xiashao.model.DataSourceType.NETWORK
import com.xiashao.model.User
import kotlinx.datetime.Clock.System

fun User.userEntityShell() = UserEntity(
    id = id,
    email = email,
    lastName = lastName,
    firstName = firstName,
    avatar = avatar,
    updateDate = System.now(),
    dataSourceType = dataSourceType ?: NETWORK
)
