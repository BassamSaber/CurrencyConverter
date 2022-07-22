package com.samz.convertcurrency.di

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.samz.convertcurrency.repo.remote.APIConstants
import com.samz.convertcurrency.repo.remote.APIInterface
import com.samz.convertcurrency.repo.remote.interceptors.ConnectivityInterceptor
import com.samz.convertcurrency.repo.remote.interceptors.HeaderInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Network Module for Providing the API Call interface
 * **/
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {


    /**
     * @param context Application context that is provided through hilt injection annotation
     *
     * @return Interceptor that check the networkConnection if it's Online or not
     */
    @Provides
    @Singleton
    fun providesConnectivityInterceptor(@ApplicationContext context: Context): ConnectivityInterceptor {
        return ConnectivityInterceptor(context)
    }

    /**
     * provide A headerInterceptor for the Http Client
     * @return headerInterceptor for the API requests
     */
    @Provides
    @Singleton
    fun providesHeaderInterceptor(): HeaderInterceptor {
        return HeaderInterceptor()
    }

    /**
     * provide logging interceptor for log the API request to the logcat
     */
    @Provides
    @Singleton
    fun providesLoggingInterceptor(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        return logging
    }

    /**
     * @param connectivityInterceptor interceptor for the network status
     * @param headerInterceptor interceptor for the APIs Headers
     * @param loggingInterceptor interceptor for logging the called requests
     *
     * provide httpclient builder with needed interceptors with defined connection&read time for
     * request call
     *
     * @return HttpClient Builder
     */
    @Provides
    @Singleton
    fun providesHttpClientBuilder(
        connectivityInterceptor: ConnectivityInterceptor,
        headerInterceptor: HeaderInterceptor,
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient.Builder {
        val httpClient = OkHttpClient.Builder()

        httpClient.addInterceptor(connectivityInterceptor)

        httpClient.addInterceptor(headerInterceptor)

        httpClient.addInterceptor(loggingInterceptor)

        httpClient.connectTimeout(60, TimeUnit.SECONDS)
        httpClient.readTimeout(60, TimeUnit.SECONDS)

        return httpClient
    }


    @Provides
    @Singleton
    fun providesGson(): Gson {
        val builder = GsonBuilder()
        return builder.create()
    }

    /**
     * provide Retrofit instance with the baseUrl and the httpClient
     *
     *@return Retrofit instance for API Calls
     */
    @Provides
    @Singleton
    fun providesRetrofit(httpClient: OkHttpClient.Builder, gson: Gson): Retrofit {
        return Retrofit.Builder()
            .baseUrl(APIConstants.baseURL)
            .client(httpClient.build())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    /**
     * @return APIClient instance for Creating API Request
     */
    @Provides
    @Singleton
    fun providesAPIClient(retrofit: Retrofit): APIInterface {
        return retrofit.create(APIInterface::class.java)
    }
}