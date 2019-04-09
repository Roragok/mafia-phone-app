/*
 * Copyright 2019 Roragok. All Rights Reserved.
 */

package com.roragok.namafia.events

import android.app.Activity
import android.content.Intent
import com.roragok.namafia.events.messages.SnackbarEvent
import com.roragok.namafia.events.messages.StringContent
import com.roragok.namafia.events.navigation.BackNavigationEvent
import kotlin.reflect.KClass

/**
 * Factory to create events
 *
 * @since 1.0.0
 */
class EventFactory {
    /**
     * Creates a new snackbar event that simply displays a message
     *
     * @param message the message to display
     * @return the event
     * @since 1.0.0
     */
    fun newSnackbarEvent(message: StringContent): Event =
        SnackbarEvent(message, onDismissListener = null, actionText = null, actionCallback = null)

    /**
     * Creates a new snackbar event that registers a callback when the snackbar is dismissed
     *
     * @param message the message to display
     * @param onDismissListener the dismiss callback
     * @return the event
     * @since 1.0.0
     */
    fun newSnackbarEvent(message: StringContent, onDismissListener: () -> Unit): Event =
        SnackbarEvent(message, onDismissListener = onDismissListener, actionText = null, actionCallback = onDismissListener)

    /**
     * Creates a new snackbar event that adds an action button and callback
     *
     * @param message the message to display
     * @param actionText the text of the action button
     * @param actionCallback the callback when action button is clicked
     * @return the event
     * @since 1.0.0
     */
    fun newSnackbarEvent(message: StringContent, actionText: Int, actionCallback: () -> Unit): Event =
        SnackbarEvent(message, onDismissListener = null, actionText = actionText, actionCallback = actionCallback)

    /**
     * Creates a new snackbar event that adds an action button and callback
     *
     * @param message the message to display
     * @param onDismissListener the dismiss callback
     * @param actionText the text of the action button
     * @param actionCallback the callback when action button is clicked
     * @return the event
     * @since 1.0.0
     */
    fun newSnackbarEvent(message: StringContent, onDismissListener: () -> Unit, actionText: Int, actionCallback: () -> Unit): Event =
        SnackbarEvent(message, onDismissListener, actionText, actionCallback)

    /**
     * Creates a new navigation event
     *
     * @param targetActivity the activity to navigate to
     * @param transition the navigation transition. Defaults to [Transition.SLIDE_LEFT]
     * @param intentFlags additional flags to set on the [Intent]
     * @param finishCurrentActivity flag if the current activity should be finished
     * @param inputData data to pass to `targetActivity`
     * @return the event
     * @since 1.0.0
     */
    fun newNavigationToActivityEvent(
        targetActivity: KClass<out Activity>,
        transition: Transition = Transition.SLIDE_LEFT,
        intentFlags: Int? = null,
        finishCurrentActivity: Boolean = false,
        inputData: InputData? = null
    ): Event =
        NavigationEvent(
            targetActivity = targetActivity,
            transition = transition,
            finishCurrentActivity = finishCurrentActivity,
            intentFlags = intentFlags,
            inputData = inputData,
            requestCode = null
        )

    /**
     * Creates a new navigation event
     *
     * @param targetActivity the activity to navigate to
     * @param transition the navigation transition. Defaults to [Transition.SLIDE_LEFT]
     * @param intentFlags additional flags to set on the [Intent]
     * @param requestCode code to use when requesting data from another activity
     * @param inputData data to pass to `targetActivity`
     * @return the event
     * @since 1.0.0
     */
    fun newNavigationToActivityEvent(
        targetActivity: KClass<out Activity>,
        transition: Transition = Transition.SLIDE_LEFT,
        intentFlags: Int? = null,
        requestCode: Int,
        inputData: InputData? = null
    ): Event =
        NavigationEvent(
            targetActivity = targetActivity,
            transition = transition,
            requestCode = requestCode,
            intentFlags = intentFlags,
            inputData = inputData,
            finishCurrentActivity = false
        )

    /**
     * Creates a new navigation event indicating the current activity should be popped
     *
     * @param transition the navigation transition. Defaults to [Transition.SLIDE_RIGHT]
     * @return the event
     * @since 1.0.0
     */
    fun newNavigateBackEvent(transition: Transition = Transition.SLIDE_RIGHT): Event =
        BackNavigationEvent(transition)

    /**
     * Creates a new navigation event indicating the result of the current activity
     *
     * @param resultCode the result; typically, this is one of [Activity.RESULT_OK] or [Activity.RESULT_CANCELED], though it can be a custom code
     * @param outputData the result of the activity. May be null, such as when `resultCode` is [Activity.RESULT_CANCELED]
     * @param transition the navigation transition. Defaults to [Transition.SLIDE_RIGHT]
     * @return the event
     * @since 1.0.0
     */
    fun newNavigateBackWithResultEvent(resultCode: Int, outputData: OutputData? = null, transition: Transition = Transition.SLIDE_RIGHT): Event =
        BackWithResultNavigationEvent(resultCode, outputData, transition)
}