/*
 * Copyright 2019 Roragok. All Rights Reserved.
 */

package com.roragok.namafia.dagger.components

import com.roragok.namafia.NamafiaApplication
import com.roragok.namafia.dagger.modules.ApplicationModule
import com.roragok.namafia.dagger.modules.BindingModule
import com.roragok.namafia.dagger.modules.MoshiModule
import com.roragok.namafia.dagger.modules.NetworkModule
import com.roragok.namafia.dagger.modules.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ApplicationModule::class,
        BindingModule::class,
        NetworkModule::class,
        MoshiModule::class,
        ViewModelModule::class
    ]
)
@Suppress("unused")
interface ApplicationComponent {
    fun inject(application: NamafiaApplication)

    @Component.Builder
    interface ApplicationBuilder {
        @BindsInstance
        fun application(application: NamafiaApplication): ApplicationBuilder

        fun build(): ApplicationComponent
    }
}