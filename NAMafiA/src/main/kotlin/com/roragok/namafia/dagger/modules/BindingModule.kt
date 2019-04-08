/*
 * Copyright 2019 Roragok. All Rights Reserved.
 */

package com.roragok.namafia.dagger.modules

import com.roragok.namafia.activities.GamesActivity
import com.roragok.namafia.dagger.scopes.ActivityScope
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class BindingModule {
    @ActivityScope
    @ContributesAndroidInjector
    abstract fun gamesActivity(): GamesActivity
}