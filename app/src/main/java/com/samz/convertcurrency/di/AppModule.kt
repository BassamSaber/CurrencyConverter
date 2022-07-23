package com.samz.convertcurrency.di

import android.content.Context
import com.samz.convertcurrency.db.DatabaseHelper
import com.samz.convertcurrency.remote.APIInterface
import com.samz.convertcurrency.repo.AppRepo
import com.samz.convertcurrency.repo.RepoInterface
import com.samz.convertcurrency.utils.ResourcesUtils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton
    fun providesResourcesUtils(@ApplicationContext context: Context) = ResourcesUtils(context)

    @Provides
    @Singleton
    fun providesAppRepository(apiClient: APIInterface, databaseHelper: DatabaseHelper) =
        AppRepo(apiClient, databaseHelper) as RepoInterface
}