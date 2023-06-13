package com.xiashao.data.database.datasource

import com.xiashao.data.database.dao.UserDao
import com.xiashao.data.database.entity.UserEntity
import com.xiashao.data.database.entity.asExternalModel
import com.xiashao.data.database.mapper.userEntityShell
import com.xiashao.model.DataSourceType
import com.xiashao.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserDBDataSource @Inject constructor(
    private val userDao: UserDao
) {
    suspend fun getUsers(): List<User> {
        return userDao.getUserEntities().map(UserEntity::asExternalModel)
    }

    fun getUsersFlow(type: DataSourceType): Flow<List<User>> {
        return userDao.getUserEntitiesStream(type).map { it.map(UserEntity::asExternalModel) }
    }

    suspend fun saveUsers(users: List<User>) {
        val userEntities = users.map(User::userEntityShell)
        userDao.insertOrIgnoreUsers(userEntities)
    }

    suspend fun update(users: List<User>) {
        val list = users.map(User::userEntityShell)
        userDao.updateUsers(list)
    }
}
