/*
 * Copyright 2019 Roragok. All Rights Reserved.
 */

package com.roragok.namafia.dagger.modules

import android.content.Context
import com.crashlytics.android.core.CrashlyticsCore
import com.roragok.namafia.BuildConfig
import com.roragok.namafia.NamafiaApplication
import com.roragok.namafia.events.EventFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule {
    @Provides
    @Singleton
    internal fun provideContext(application: NamafiaApplication): Context = application

    @Provides
    @Singleton
    internal fun provideCrashlytics(): CrashlyticsCore =
        CrashlyticsCore.Builder().disabled(!BuildConfig.FABRIC_ENABLED).build()

    @Provides
    @Singleton
    internal fun provideEventFactory(): EventFactory = EventFactory()
}