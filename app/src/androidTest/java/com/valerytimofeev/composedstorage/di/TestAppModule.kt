package com.valerytimofeev.composedstorage.di

import android.content.Context
import androidx.room.Room
import com.valerytimofeev.composedstorage.data.local.StorageDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
class TestAppModule {

    @Provides
    @Named("test_db")
    fun provideInMemoryDatabase(@ApplicationContext context: Context) =
        Room.inMemoryDatabaseBuilder(context, StorageDatabase::class.java)
            .allowMainThreadQueries()
            .build()
}