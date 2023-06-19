package com.xiashao.data.network.datasource

import com.xiashao.data.network.api.NetworkUser
import com.xiashao.data.network.api.UserApi
import com.xiashao.model.User
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserNetworkDataSource @Inject constructor(
    private val userApi: UserApi
) {
    suspend fun getUsers(pageIndex: Int, pageSize: Int): List<User> {
        return userApi.fetchUser(pageIndex, pageSize).data.map(NetworkUser::asExternalModel)
    }
}
