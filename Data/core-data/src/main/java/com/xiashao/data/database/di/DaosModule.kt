package com.xiashao.data.database.di

import com.xiashao.data.database.AppDatabase
import com.xiashao.data.database.dao.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DaosModule {

    @Provides
    fun providesAuthorDao(
        database: AppDatabase,
    ): UserDao = database.userDao()
}
