/*
 * Copyright 2019 Roragok. All Rights Reserved.
 */

package com.roragok.namafia.dagger.modules

import androidx.lifecycle.ViewModel
import com.roragok.namafia.activities.viewmodels.GamesViewModel
import com.roragok.namafia.dagger.factories.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(GamesViewModel::class)
    abstract fun gamesViewModel(viewModel: GamesViewModel): ViewModel
}