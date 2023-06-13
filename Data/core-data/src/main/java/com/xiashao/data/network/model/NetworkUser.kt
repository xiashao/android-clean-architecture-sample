package com.xiashao.data.network.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.telenav.scoutivi.model.DataSourceType.NETWORK
import com.telenav.scoutivi.model.User

@Keep
data class NetworkUser(
    val avatar: String,
    val email: String,
    @field:SerializedName("first_name")
    val firstName: String,
    val id: Int,
    @field:SerializedName("last_name")
    val lastName: String,
)

fun NetworkUser.asExternalModel() = User(
    id = id,
    firstName = firstName,
    avatar = avatar,
    email = email,
    lastName = lastName,
    dataSourceType = NETWORK
)
