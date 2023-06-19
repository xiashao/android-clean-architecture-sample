package com.xiashao.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.xiashao.model.User
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserKeyValueDataSource @Inject constructor(
    private val userPreferences: DataStore<Preferences>
) {
    private val gson = Gson()
    private val classType = object : TypeToken<List<User>>() {}.type

    companion object {
        val KEY_USERS = stringPreferencesKey("users")
    }

    val userDataStream = userPreferences.data
        .map {
            val userJson = it[KEY_USERS] ?: ""
            val users: List<User> = gson.fromJson(userJson, classType)
            users
        }

    suspend fun saveUsers(users: List<User>) {
        userPreferences.edit {
            it[KEY_USERS] = gson.toJson(users)
        }
    }

    // You should also handle IOExceptions here
    suspend fun getUsers(): List<User> {
        val preferences = userPreferences.data.first()
        val userJson = preferences[KEY_USERS] ?: ""
        return if (userJson.isEmpty()) emptyList() else gson.fromJson(userJson, classType)
    }
}
