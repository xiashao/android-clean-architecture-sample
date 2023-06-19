package com.xiashao.data.network.api

import retrofit2.http.GET
import retrofit2.http.Query

interface UserApi {

    @GET("/api/users")
    suspend fun fetchUser(
        @Query("page") page: Int = 1,
        @Query("per_page") perPage: Int = 10
    ): ApiResponse<List<NetworkUser>>
}

data class ApiResponse<T>(
    val page: Int,
    val per_page: Int,
    val total: Int,
    val total_pages: Int,
    val data: T
)

data class NetworkUser(
    val id: Int,
    val email: String,
    val first_name: String,
    val last_name: String,
    val avatar: String
) {
    fun asExternalModel() = com.xiashao.model.User(
        id = id,
        email = email,
        firstName = first_name,
        lastName = last_name,
        avatar = avatar
    )
}