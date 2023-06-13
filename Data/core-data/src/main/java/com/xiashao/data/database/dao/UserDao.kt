package com.xiashao.data.database.dao

import androidx.room.*
import com.xiashao.data.database.entity.UserEntity
import com.xiashao.model.DataSourceType
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Query(
        value = """
        SELECT * FROM users where datasource_type =:type
    """
    )
    fun getUserEntitiesStream(type: DataSourceType): Flow<List<UserEntity>>

    @Query(value = "SELECT * FROM users")
    suspend fun getUserEntities(): List<UserEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOrIgnoreUsers(userEntities: List<UserEntity>): List<Long>

    @Update
    suspend fun updateUsers(entities: List<UserEntity>)

    @Transaction
    suspend fun upsertUsers(entities: List<UserEntity>) = upsert(
        items = entities,
        insertMany = ::insertOrIgnoreUsers,
        updateMany = ::updateUsers
    )

    @Query(
        value = """
            DELETE FROM users
            WHERE id in (:ids)
        """
    )
    suspend fun deleteUsers(ids: List<Int>)
}
