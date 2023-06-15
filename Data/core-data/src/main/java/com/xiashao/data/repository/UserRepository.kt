//package com.xiashao.data.repository
//
//import com.xiashao.data.database.datasource.UserDBDataSource
//import com.xiashao.data.datastore.UserKeyValueDataSource
//import com.xiashao.model.DataSourceType
//import com.xiashao.model.User
//import com.xiashao.data.network.datasource.UserNetworkDataSource
//import kotlinx.coroutines.flow.Flow
//import javax.inject.Inject
//
//class UserRepository @Inject constructor(
//    private val userNetworkDataSource: UserNetworkDataSource,
//    private val userDBDataSource: UserDBDataSource,
//    private val userKeyValueDataSource: UserKeyValueDataSource
//
//) {
//
//    val userDSStream = userKeyValueDataSource.userDataStream
//
//    fun getUsersFromDBAsFlow(type: DataSourceType): Flow<List<User>> {
//        return userDBDataSource.getUsersFlow(type)
//    }
//
//    suspend fun fetchUsers(): List<User> {
//        // from db
//        var users = userDBDataSource.getUsers()
//        if (users.isEmpty()) {
//            // from net
//            users = userNetworkDataSource.getUsers(1, 12)
//            saveToDatabase(users)
//        }
//        return users
//    }
//
//    suspend fun saveToDatabase(users: List<User>) {
//        userDBDataSource.saveUsers(users)
//    }
//
//    suspend fun saveToDataStore(users: List<User>) {
//        userKeyValueDataSource.saveUsers(users)
//    }
//
//    suspend fun updateToDB(users: List<User>) {
//        userDBDataSource.update(users)
//    }
//}
