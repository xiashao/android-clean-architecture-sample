package com.xiashao.data.database.entity

import androidx.annotation.Keep
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.xiashao.model.DataSourceType
import com.xiashao.model.User
import kotlinx.datetime.Instant

@Keep
@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey
    val id: Int,
    val avatar: String,
    val email: String,
    @ColumnInfo(name = "first_name")
    val firstName: String,
    @ColumnInfo(name = "last_name")
    val lastName: String,
    @ColumnInfo(name = "update_date")
    val updateDate: Instant,

    // auto migration 1->2
    @ColumnInfo(name = "datasource_type")
    val dataSourceType: DataSourceType,
)

fun UserEntity.asExternalModel() = User(
    id = id,
    firstName = firstName,
    avatar = avatar,
    email = email,
    lastName = lastName,
    dataSourceType = dataSourceType
)
