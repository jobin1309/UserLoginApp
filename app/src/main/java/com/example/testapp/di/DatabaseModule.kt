package com.example.testapp.di

import android.content.Context
import androidx.room.Room
import com.example.testapp.data.local.UserDao
import com.example.testapp.data.local.UserDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): UserDatabase {
        return Room.databaseBuilder(
            context,
            UserDatabase::class.java,
            "app_database"
        ).build()
    }

    @Provides
    fun provideUserDao(database: UserDatabase): UserDao {
        return database.userDao()
    }
}
