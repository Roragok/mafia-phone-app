/*
 * Copyright 2019 Roragok. All Rights Reserved.
 */

package com.roragok.namafia.dagger.modules

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
@Suppress("unused")
class MoshiModule {
    @Provides
    @Singleton
    internal fun provideNetworkMoshi(): Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()
}