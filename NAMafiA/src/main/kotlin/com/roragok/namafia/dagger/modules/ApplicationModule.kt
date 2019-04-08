/*
 * Copyright 2019 Roragok. All Rights Reserved.
 */

package com.roragok.namafia.dagger.modules

import com.crashlytics.android.core.CrashlyticsCore
import com.roragok.namafia.BuildConfig
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule {
    @Provides
    @Singleton
    internal fun provideCrashlytics(): CrashlyticsCore =
        CrashlyticsCore.Builder().disabled(!BuildConfig.FABRIC_ENABLED).build()
}