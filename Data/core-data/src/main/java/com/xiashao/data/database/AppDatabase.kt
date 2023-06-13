package com.xiashao.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.xiashao.data.database.dao.UserDao
import com.xiashao.data.database.entity.UserEntity

const val DATABASE_NAME = "demo-database.db"
const val DATABASE_VERSION = 1

@Database(
    entities = [
        UserEntity::class
    ],
    version = DATABASE_VERSION,
    exportSchema = false
)
@TypeConverters(
    InstantConverter::class,
    DatasourceTypeConverter::class
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}
