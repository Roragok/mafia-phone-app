/*
 * Copyright 2019 Roragok. All Rights Reserved.
 */

package com.dds.roragok.namafia.events.messages

import android.app.Activity
import android.view.ViewGroup
import com.dds.roragok.namafia.events.AbstractOneTimeEvent
import com.dds.roragok.namafia.extensions.showSnackbar
import timber.log.Timber

internal data class SnackbarEvent(
    val message: StringContent,
    val onDismissListener: (() -> Unit)?,
    val actionText: Int?,
    val actionCallback: (() -> Unit)?
) : AbstractOneTimeEvent() {

    override fun executeEvent(activity: Activity, viewGroupId: Int?) {
        Timber.d("handling snackbar message")

        val message = this.message.resolve(activity)
        val viewGroup = activity.findViewById<ViewGroup>(viewGroupId ?: DEFAULT_VIEWGROUP_ID)

        when {
            // lines are long, so using brace notation for consistency
            onDismissListener != null && actionText != null && actionCallback != null -> {
                viewGroup.showSnackbar(message, dismissListener = onDismissListener, actionResId = actionText, action = actionCallback)
            }

            actionText != null && actionCallback != null                              -> {
                viewGroup.showSnackbar(message, actionResId = actionText, action = actionCallback)
            }

            onDismissListener != null                                                 -> {
                viewGroup.showSnackbar(message, dismissListener = onDismissListener)
            }

            else                                                                      -> {
                viewGroup.showSnackbar(message)
            }
        }
    }

    companion object {
        private const val DEFAULT_VIEWGROUP_ID = android.R.id.content
    }
}