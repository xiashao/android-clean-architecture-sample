//package com.xiashao.data.network.api
//
//import com.xiashao.data.network.ApiResponse
//import com.xiashao.data.network.model.NetworkUser
//import retrofit2.http.GET
//import retrofit2.http.Query
//
//interface UserApi {
//
//    @GET("/api/users")
//    suspend fun fetchUser(
//        @Query("page") page: Int = 1,
//        @Query("per_page") perPage: Int = 10
//    ): ApiResponse<List<NetworkUser>>
//}
