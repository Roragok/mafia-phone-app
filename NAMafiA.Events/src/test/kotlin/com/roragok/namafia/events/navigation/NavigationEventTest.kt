/*
 * Copyright 2019 Roragok. All Rights Reserved.
 */

package com.roragok.namafia.events.navigation

import android.app.Activity
import android.content.Intent
import com.roragok.namafia.events.InputData
import com.roragok.namafia.events.IntentExtra
import com.roragok.namafia.events.NavigationEvent
import com.roragok.namafia.events.Transition
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkConstructor
import io.mockk.verify
import io.mockk.verifyOrder
import org.junit.After
import org.junit.Test

class NavigationEventTest {
    private val activity = mockk<Activity>(relaxUnitFun = true)

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `test no intent flags`() {
        mockkConstructor(Intent::class)

        val transition = Transition.SLIDE_UP

        val event = NavigationEvent(TestActivity::class, transition, intentFlags = null, finishCurrentActivity = false, requestCode = null, inputData = null)
        event.resolve(activity)

        verify(exactly = 0) {
            anyConstructed<Intent>().addFlags(any())
        }

        verifyOrder {
            activity.startActivity(any())
            activity.overridePendingTransition(transition.enterAnim, transition.exitAnim)
        }
    }

    @Test
    fun `test have intent flags`() {
        mockkConstructor(Intent::class)

        val intent = mockk<Intent>()
        val transition = Transition.SLIDE_UP
        val intentFlags = Intent.FLAG_ACTIVITY_NEW_TASK and Intent.FLAG_ACTIVITY_CLEAR_TASK

        every { anyConstructed<Intent>().addFlags(intentFlags) } returns intent

        val event = NavigationEvent(TestActivity::class, transition, intentFlags = intentFlags, finishCurrentActivity = false, requestCode = null, inputData = null)
        event.resolve(activity)

        verifyOrder {
            anyConstructed<Intent>().addFlags(intentFlags)
            activity.startActivity(any())
            activity.overridePendingTransition(transition.enterAnim, transition.exitAnim)
        }
    }

    @Test
    fun `test finish current activity`() {
        mockkConstructor(Intent::class)

        val transition = Transition.SLIDE_UP

        val event = NavigationEvent(TestActivity::class, transition, intentFlags = null, finishCurrentActivity = true, requestCode = null, inputData = null)
        event.resolve(activity)

        verifyOrder {
            activity.startActivity(any())
            activity.overridePendingTransition(transition.enterAnim, transition.exitAnim)
            activity.finish()
        }
    }

    @Test
    fun `test does not finish current activity`() {
        mockkConstructor(Intent::class)

        val transition = Transition.SLIDE_UP

        val event = NavigationEvent(TestActivity::class, transition, intentFlags = null, finishCurrentActivity = false, requestCode = null, inputData = null)
        event.resolve(activity)

        verify(exactly = 0) {
            activity.finish()
        }

        verifyOrder {
            activity.startActivity(any())
            activity.overridePendingTransition(transition.enterAnim, transition.exitAnim)
        }
    }

    @Test
    fun `test intent extras added`() {
        mockkConstructor(Intent::class)

        val transition = Transition.SLIDE_UP
        val inputData = mockk<InputData>()
        val intent = mockk<Intent>()

        every { anyConstructed<Intent>().putExtra(IntentExtra.INPUT_DATA, inputData) } returns intent

        val event = NavigationEvent(TestActivity::class, transition, intentFlags = null, finishCurrentActivity = false, requestCode = null, inputData = inputData)
        event.resolve(activity)

        verify {
            anyConstructed<Intent>().putExtra(IntentExtra.INPUT_DATA, inputData)
        }

        verifyOrder {
            activity.startActivity(any())
            activity.overridePendingTransition(transition.enterAnim, transition.exitAnim)
        }
    }

    @Test
    fun `test have request code`() {
        mockkConstructor(Intent::class)

        val transition = Transition.SLIDE_UP
        val inputData = mockk<InputData>()
        val intent = mockk<Intent>()

        every { anyConstructed<Intent>().putExtra(IntentExtra.INPUT_DATA, inputData) } returns intent

        val event = NavigationEvent(TestActivity::class, transition, intentFlags = null, finishCurrentActivity = false, requestCode = 404, inputData = inputData)
        event.resolve(activity)

        verify {
            anyConstructed<Intent>().putExtra(IntentExtra.INPUT_DATA, inputData)
        }

        verifyOrder {
            activity.startActivityForResult(any(), 404)
            activity.overridePendingTransition(transition.enterAnim, transition.exitAnim)
        }
    }

    private class TestActivity : Activity()
}