//package com.xiashao.data.datastore.di
//
//import android.content.Context
//import androidx.datastore.core.DataStore
//import androidx.datastore.dataStoreFile
//import androidx.datastore.preferences.core.PreferenceDataStoreFactory
//import androidx.datastore.preferences.core.Preferences
//import com.xiashao.foundation.di.IoDispatcher
//import dagger.Module
//import dagger.Provides
//import dagger.hilt.InstallIn
//import dagger.hilt.android.qualifiers.ApplicationContext
//import dagger.hilt.components.SingletonComponent
//import kotlinx.coroutines.CoroutineDispatcher
//import kotlinx.coroutines.CoroutineScope
//import kotlinx.coroutines.SupervisorJob
//import javax.inject.Singleton
//
//@Module
//@InstallIn(SingletonComponent::class)
//object DataStoreModule {
//
//
//    @Provides
//    @Singleton
//    fun providesUserPreferencesDataStore(
//        @ApplicationContext context: Context,
//        @IoDispatcher ioDispatcher: CoroutineDispatcher
//    ): DataStore<Preferences> =
//        PreferenceDataStoreFactory.create(
//            scope = CoroutineScope(ioDispatcher + SupervisorJob()),
//        ) {
//            context.dataStoreFile("user_preferences.preferences_pb")
//        }
//}
