/*
 * Copyright 2019 Roragok. All Rights Reserved.
 */

package com.roragok.namafia.api.okhttp3.interceptors

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.unmockkAll
import io.mockk.verify
import okhttp3.Interceptor.Chain
import okhttp3.Request
import org.junit.After
import org.junit.Before
import org.junit.Test

/**
 * Unit tests for [DefaultHeadersInterceptor]
 *
 * @since 1.0.0
 */
class DefaultHeadersInterceptorTest {
    private lateinit var interceptor: DefaultHeadersInterceptor
    private val chain = mockk<Chain>(relaxed = true)
    private val requestCaptor = slot<Request>()

    @Before
    fun setUp() {
        interceptor = DefaultHeadersInterceptor()
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `test accept_header added`() {
        val request = Request.Builder().url("http://www.test.com").build()

        every { chain.request() } returns request

        interceptor.intercept(chain)

        verify { chain.proceed(capture(requestCaptor)) }

        assertThat(requestCaptor.captured.header("Accept")).isEqualTo("application/json")
    }

    @Test
    fun `test accept_header not added - already exists`() {
        val request = Request.Builder().url("http://www.test.com").header("Accept", "custom value").build()

        every { chain.request() } returns request

        interceptor.intercept(chain)

        verify { chain.proceed(capture(requestCaptor)) }

        assertThat(requestCaptor.captured.header("Accept")).isEqualTo("custom value")
    }

    @Test
    fun `test content type_added`() {
        val request = Request.Builder().url("http://www.test.com").build()

        every { chain.request() } returns request

        interceptor.intercept(chain)

        verify { chain.proceed(capture(requestCaptor)) }

        assertThat(requestCaptor.captured.header("ContentType")).isEqualTo("application/json")
    }

    @Test
    fun `test content type not added - already exists`() {
        val request = Request.Builder().url("http://www.test.com").header("ContentType", "custom value").build()

        every { chain.request() } returns request

        interceptor.intercept(chain)

        verify { chain.proceed(capture(requestCaptor)) }

        assertThat(requestCaptor.captured.header("ContentType")).isEqualTo("custom value")
    }
}