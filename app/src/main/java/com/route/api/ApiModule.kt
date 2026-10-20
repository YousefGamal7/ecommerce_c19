package com.route.api

import android.util.Log
import com.route.api.helpers.SearchInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {

    @Provides
    @Singleton
    fun provideOkHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor { message -> Log.e("RetroFit", message) }
        interceptor.setLevel(level = HttpLoggingInterceptor.Level.BODY)
        return interceptor
    }

    @Provides
    @Singleton
    fun provideSearchInterceptor(): SearchInterceptor {
        return SearchInterceptor()
    }

    @Provides
    @Singleton
    fun provideOkHttp(
        loggingInterceptor: HttpLoggingInterceptor,
        searchInterceptor: SearchInterceptor,
    ): OkHttpClient {
        val okhttp = OkHttpClient.Builder()
            .addInterceptor(searchInterceptor)
            .addInterceptor(loggingInterceptor)
            .build()
        return okhttp
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttp: OkHttpClient): Retrofit {
        val retrofit = Retrofit.Builder()
            .client(okHttp)
            .baseUrl("https://ecommerce.routemisr.com/api/")
            .addConverterFactory(
                GsonConverterFactory.create()
            ).build()
        return retrofit
    }

    @Provides
    @Singleton
    fun provideServices(
        retrofit: Retrofit
    ): ApiServices {
        return retrofit.create(ApiServices::class.java)
    }
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class HeadersInterceptor

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class LoggingInterceptor
