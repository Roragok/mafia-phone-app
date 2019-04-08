/*
 * Copyright 2019 Roragok. All Rights Reserved.
 */

package com.roragok.namafia.dagger.modules

import android.content.Context
import android.os.Build
import android.os.Build.VERSION
import android.os.storage.StorageManager
import com.dds.roragok.namafia.api.okhttp3.interceptors.DefaultHeadersInterceptor
import com.dds.roragok.namafia.api.okhttp3.interceptors.LoggingInterceptor
import com.dds.roragok.namafia.api.retrofit.NamfiaService
import com.roragok.namafia.BuildConfig
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeUnit.SECONDS
import javax.inject.Singleton

@Module
@Suppress("unused")
class NetworkModule {
    @Provides
    @Singleton
    internal fun provideCache(context: Context): Cache {
        val cacheDirectory = context.cacheDir

        val storageManager = context.getSystemService(StorageManager::class.java)

        val cacheSize = if (VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            storageManager.getCacheQuotaBytes(storageManager.getUuidForPath(cacheDirectory))
        } else {
            10 * 1024 * 1024 // 10 MB
        }

        return Cache(cacheDirectory, cacheSize)
    }

    @Provides
    @Singleton
    internal fun provideOkHttpClient(cache: Cache): OkHttpClient {
        // interceptor order is important
        return OkHttpClient.Builder()
            .cache(cache)
            .addNetworkInterceptor(DefaultHeadersInterceptor())
            .addNetworkInterceptor(LoggingInterceptor())
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(0, SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient, moshi: Moshi): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.API_SERVER)
            .client(client)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    @Provides
    @Singleton
    internal fun provideNamfiaService(retrofit: Retrofit): NamfiaService = retrofit.create(NamfiaService::class.java)
}