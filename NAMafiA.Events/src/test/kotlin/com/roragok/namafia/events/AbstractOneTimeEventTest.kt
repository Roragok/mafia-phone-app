/*
 * Copyright 2019 Roragok. All Rights Reserved.
 */

package com.roragok.namafia.events

import android.app.Activity
import com.google.common.truth.Truth.assertThat
import io.mockk.mockk
import org.junit.Test

class AbstractOneTimeEventTest {
    private val activity = mockk<Activity>()

    @Test
    fun `test event executed only once`() {
        val oneTimeEvent = TestEvent()

        assertThat(oneTimeEvent.executeCount).isEqualTo(0)

        oneTimeEvent.resolve(activity)
        oneTimeEvent.resolve(activity)

        assertThat(oneTimeEvent.executeCount).isEqualTo(1)
    }

    private class TestEvent : AbstractOneTimeEvent() {
        var executeCount = 0

        override fun executeEvent(activity: Activity, viewGroupId: Int?) {
            executeCount++
        }
    }
}