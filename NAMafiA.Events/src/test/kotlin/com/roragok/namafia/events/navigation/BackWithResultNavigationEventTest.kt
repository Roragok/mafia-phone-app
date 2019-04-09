/*
 * Copyright 2019 Roragok. All Rights Reserved.
 */

package com.roragok.namafia.events.navigation

import android.app.Activity
import android.content.Intent
import com.roragok.namafia.events.BackWithResultNavigationEvent
import com.roragok.namafia.events.IntentExtra
import com.roragok.namafia.events.OutputData
import com.roragok.namafia.events.Transition
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkConstructor
import io.mockk.slot
import io.mockk.verify
import io.mockk.verifyOrder
import org.junit.After
import org.junit.Test

class BackWithResultNavigationEventTest {
    private val activity = mockk<Activity>(relaxUnitFun = true)

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `test no output data provided`() {
        val transition = Transition.SLIDE_UP

        val event = BackWithResultNavigationEvent(resultCode = 404, outputData = null, transition = transition)
        event.resolve(activity)

        verifyOrder {
            activity.setResult(404)
            activity.finish()
            activity.overridePendingTransition(transition.enterAnim, transition.exitAnim)
        }
    }

    @Test
    fun `test with output data provided`() {
        mockkConstructor(Intent::class)

        val outputData = mockk<OutputData>()
        val transition = Transition.SLIDE_UP
        val intentSlot = slot<Intent>()

        every { anyConstructed<Intent>().putExtra(IntentExtra.OUTPUT_DATA, outputData) } returns mockk()

        val event = BackWithResultNavigationEvent(resultCode = 404, outputData = outputData, transition = transition)
        event.resolve(activity)

        verify {
            activity.setResult(404, capture(intentSlot))
            activity.finish()
            activity.overridePendingTransition(transition.enterAnim, transition.exitAnim)
        }

        verifyOrder {
            intentSlot.captured.putExtra(IntentExtra.OUTPUT_DATA, outputData)
            activity.setResult(404, intentSlot.captured)
            activity.finish()
            activity.overridePendingTransition(transition.enterAnim, transition.exitAnim)
        }
    }
}