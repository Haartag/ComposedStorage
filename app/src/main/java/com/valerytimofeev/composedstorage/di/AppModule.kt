package com.valerytimofeev.composedstorage.di

import android.content.Context
import androidx.room.Room
import com.valerytimofeev.composedstorage.data.local.StorageDAO
import com.valerytimofeev.composedstorage.data.local.StorageDatabase
import com.valerytimofeev.composedstorage.repositories.DefaultStorageRepository
import com.valerytimofeev.composedstorage.repositories.StorageRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideStorageDao(storageDatabase: StorageDatabase): StorageDAO {
        return storageDatabase.storageDao()
    }

    @Singleton
    @Provides
    fun providesStorageDatabase(@ApplicationContext appContext: Context): StorageDatabase {
        return Room.databaseBuilder(
            appContext,
            StorageDatabase::class.java,
            "StorageDB"
        )
            .createFromAsset("database/storage.db")
            //for test only
            //.fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideDefaultStorageRepository(
        dao: StorageDAO
    ) = DefaultStorageRepository(dao) as StorageRepository
}