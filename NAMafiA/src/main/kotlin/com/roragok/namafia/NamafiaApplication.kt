/*
 * Copyright 2019 Roragok. All Rights Reserved.
 */

package com.roragok.namafia

import android.app.Activity
import android.app.Application
import com.crashlytics.android.Crashlytics
import com.crashlytics.android.core.CrashlyticsCore
import com.roragok.namafia.dagger.components.DaggerApplicationComponent
import com.roragok.namafia.timber.CrashlyticsTree
import com.roragok.namafia.timber.NamfiaDebugTree
import com.roragok.namafia.timber.NoLogTree
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import io.fabric.sdk.android.Fabric
import timber.log.Timber
import javax.inject.Inject

/**
 * Main application for NAMafiA
 *
 * @version 1.0.0
 * @see 1.0.0
 */
open class NamafiaApplication : Application(), HasActivityInjector {
    @Inject lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>
    @Inject lateinit var crashlyticsCore: CrashlyticsCore

    override fun onCreate() {
        super.onCreate()

        configureDagger()
        configureFabric()
        configureTimber()
    }

    override fun activityInjector(): AndroidInjector<Activity>? = dispatchingAndroidInjector

    protected open fun configureTimber() {
        @Suppress("ConstantConditionIf")
        if (BuildConfig.DEBUG || BuildConfig.BUILD_TYPE == BUILD_QA) {
            Timber.plant(NamfiaDebugTree())

            Timber.d("debug tree planted")
        } else {
            Timber.plant(NoLogTree())
        }

        @Suppress("ConstantConditionIf")
        if (BuildConfig.FABRIC_ENABLED) {
            Timber.plant(CrashlyticsTree())

            Timber.d("crashlytics tree planted")
        }
    }

    protected open fun configureFabric() {
        Fabric.with(this, Crashlytics.Builder().core(crashlyticsCore).build())
    }

    protected open fun configureDagger() {
        Timber.d("configuring dagger")

        DaggerApplicationComponent
            .builder()
            .application(this)
            .build()
            .inject(this)

        Timber.d("Dagger configuration complete")
    }

    companion object {
        private const val BUILD_QA = "qa"
    }
}