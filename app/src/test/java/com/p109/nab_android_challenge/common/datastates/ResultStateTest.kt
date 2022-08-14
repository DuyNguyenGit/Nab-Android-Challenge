package com.p109.nab_android_challenge.common.datastates

import org.junit.Assert.*

import org.junit.Before
import org.junit.Test

class ResultStateTest {

    private val data = "DATA"
    private val errorMessage = "ERROR MSG"
    private val error = ApiError(cod = "401", message = errorMessage)
    private val successResultState = ResultState.success(data)
    private val errorResultState = ResultState.error(error, "")
    private val loadingResultState = ResultState.loading("")
    @Before
    fun setUp() {
    }

    @Test
    fun getStatus() {
        assertEquals(Status.SUCCESS, successResultState.status)
        assertEquals(Status.ERROR, errorResultState.status)
        assertEquals(Status.LOADING, loadingResultState.status)
    }

    @Test
    fun getData() {
        assertEquals(data, successResultState.data)
        assertEquals("", errorResultState.data)
        assertEquals("", loadingResultState.data)
    }

    @Test
    fun getMessage() {
        assertNull(successResultState.apiError)
        assertEquals(errorMessage, errorResultState.apiError?.message)
        assertNull(loadingResultState.apiError)
    }
}