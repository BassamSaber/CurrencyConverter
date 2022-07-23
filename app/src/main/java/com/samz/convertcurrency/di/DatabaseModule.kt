package com.samz.convertcurrency.di

import android.content.Context
import androidx.room.Room
import com.samz.convertcurrency.db.AppDatabase
import com.samz.convertcurrency.db.DatabaseHelper
import com.samz.convertcurrency.db.dao.HistoryConversionDao
import com.samz.convertcurrency.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun providesDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, Constants.databaseName)
            .fallbackToDestructiveMigration().build()
    }

    @Singleton
    @Provides
    fun providesConversionDao(appDatabase: AppDatabase): HistoryConversionDao {
        return appDatabase.conversionDao()
    }

    @Singleton
    @Provides
    fun providesDatabaseHelper(conversionDao: HistoryConversionDao): DatabaseHelper {
        return DatabaseHelper(conversionDao)
    }
}