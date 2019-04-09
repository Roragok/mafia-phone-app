/*
 * Copyright 2019 Roragok. All Rights Reserved.
 */

package com.roragok.namafia.activities

import android.content.Intent
import android.widget.ImageButton
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.dds.roragok.namafia.events.Event
import com.dds.roragok.namafia.events.EventFactory
import com.roragok.namafia.R
import com.roragok.namafia.activities.viewmodels.AbstractViewModel
import com.roragok.namafia.dagger.factories.ViewModelFactory
import dagger.android.support.DaggerAppCompatActivity
import timber.log.Timber
import javax.inject.Inject
import kotlin.reflect.KClass

abstract class AbstractActivity : DaggerAppCompatActivity() {
    @Inject lateinit var eventFactory: EventFactory
    @Inject lateinit var viewModelFactory: ViewModelFactory
    private var addListeners = true

    override fun onStart() {
        super.onStart()

        configureToolbar()
    }

    override fun onBackPressed() {
        Timber.d("back pressed")

        if (isTaskRoot) {
            Timber.d("root activity - ignoring back press")

            return
        }

        finish()

        eventFactory.newNavigateBackEvent().resolve(this)
    }

    /**
     * Convenience method to get a view model. Also adds listeners for [Event]s.
     * If called multiple times, only the first view model will be observing.
     *
     * @param klass the class of the view model to get
     * @since 1.0.0
     */
    protected fun <T : ViewModel> getViewModel(klass: KClass<T>): T {
        val viewModel = ViewModelProviders.of(this, viewModelFactory).get(klass.java)

        if (addListeners && viewModel is AbstractViewModel) {
            addListeners = false

            viewModel.eventsObservable.observe(this, Observer { event ->
                Timber.d("event observed: $event")

                event.resolve(this)
            })
        }

        return viewModel
    }

    private fun configureToolbar() {
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(false)
            it.setDisplayShowTitleEnabled(false)
        }

        val backArrow = findViewById<ImageButton>(R.id.back)

        backArrow?.setOnClickListener {
            onBackPressed()
        }

        val toolbarTitle = findViewById<TextView>(R.id.toolbar_title)

        if (toolbarTitle != null) {
            val intent = Intent(this, javaClass)
            intent.`package` = packageName

            val rInfo = packageManager.resolveActivity(intent, 0)
            val aInfo = rInfo.activityInfo

            if (aInfo.labelRes != 0) {
                toolbarTitle.setText(aInfo.labelRes)
            }
        }
    }
}