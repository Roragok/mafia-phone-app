/*
 * Copyright 2019 Roragok. All Rights Reserved.
 */

package com.roragok.namafia.events.messages

import android.app.Activity
import android.view.ViewGroup
import com.roragok.namafia.extensions.showSnackbar
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.runs
import io.mockk.verify
import org.junit.After
import org.junit.Before
import org.junit.Test

class SnackbarEventTest {
    private lateinit var event: SnackbarEvent
    private val activity = mockk<Activity>()
    private val viewGroup = mockk<ViewGroup>()

    @Before
    fun setUp() {
        mockkStatic("com.roragok.namafia.extensions.ViewGroupExtensionsKt")
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `test only message provided`() {
        every { activity.findViewById<ViewGroup>(android.R.id.content) } returns viewGroup
        every { viewGroup.showSnackbar("test") } just runs

        event = SnackbarEvent(StringContent("test"), onDismissListener = null, actionText = null, actionCallback = null)
        event.resolve(activity)

        verify {
            viewGroup.showSnackbar("test")
        }
    }

    @Test
    fun `test message and dismiss listener provided`() {
        val dismissListener = {}

        every { activity.findViewById<ViewGroup>(android.R.id.content) } returns viewGroup
        every { viewGroup.showSnackbar("test", dismissListener = dismissListener) } just runs

        event = SnackbarEvent(StringContent("test"), onDismissListener = dismissListener, actionText = null, actionCallback = null)
        event.resolve(activity)

        verify {
            viewGroup.showSnackbar("test", dismissListener = dismissListener)
        }
    }

    @Test
    fun `test message and actions provided`() {
        val action = {}

        every { activity.findViewById<ViewGroup>(android.R.id.content) } returns viewGroup
        every { viewGroup.showSnackbar("test", actionResId = android.R.string.ok, action = action) } just runs

        event = SnackbarEvent(StringContent("test"), onDismissListener = null, actionText = android.R.string.ok, actionCallback = action)
        event.resolve(activity)

        verify {
            viewGroup.showSnackbar("test", actionResId = android.R.string.ok, action = action)
        }
    }

    @Test
    fun `test message, dismiss listener, and actions provided`() {
        val dismissListener = {}
        val action = {}

        every { activity.findViewById<ViewGroup>(android.R.id.content) } returns viewGroup
        every { viewGroup.showSnackbar("test", dismissListener = dismissListener, actionResId = android.R.string.ok, action = action) } just runs

        event = SnackbarEvent(StringContent("test"), onDismissListener = dismissListener, actionText = android.R.string.ok, actionCallback = action)
        event.resolve(activity)

        verify {
            viewGroup.showSnackbar("test", dismissListener = dismissListener, actionResId = android.R.string.ok, action = action)
        }
    }
}