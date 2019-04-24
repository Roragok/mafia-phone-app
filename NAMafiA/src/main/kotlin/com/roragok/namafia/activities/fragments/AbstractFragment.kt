/*
 * Copyright 2019 Roragok. All Rights Reserved.
 */

package com.roragok.namafia.activities.fragments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.roragok.namafia.dagger.factories.ViewModelFactory
import dagger.android.support.DaggerFragment
import javax.inject.Inject
import kotlin.reflect.KClass

abstract class AbstractFragment : DaggerFragment() {
    @Inject lateinit var viewModelFactory: ViewModelFactory

    /**
     * Convenience method to get a view model.
     *
     * @param klass the class of the view model to get
     * @param activityScope flag if the view model should be scoped to the activity. Defaults to false
     * @return null if the fragment is not associated with an activity
     * @since 1.0.0
     */
    protected fun <T : ViewModel> getViewModel(klass: KClass<T>, activityScope: Boolean = false): T? {
        val activity = activity ?: return null

        val viewModelProvider = if (activityScope) {
            ViewModelProviders.of(activity, viewModelFactory)
        } else {
            ViewModelProviders.of(this, viewModelFactory)
        }

        return viewModelProvider.get(klass.java)
    }
}