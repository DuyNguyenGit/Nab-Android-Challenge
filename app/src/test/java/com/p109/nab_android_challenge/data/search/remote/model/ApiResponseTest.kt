package com.p109.nab_android_challenge.data.search.remote.model

import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Response

@RunWith(JUnit4::class)
class ApiResponseTest {

    @Before
    fun setUp() {
    }

    @Test
    fun exception() {
        val exception = Exception("foo")
        val (errorMessage) = ApiResponse.create<String>(exception)
        MatcherAssert.assertThat(errorMessage, CoreMatchers.`is`("foo"))
    }

    @Test
    fun success() {
        val apiResponse: ApiSuccessResponse<String> = ApiResponse
            .create<String>(Response.success("foo")) as ApiSuccessResponse<String>
        MatcherAssert.assertThat(apiResponse.body, CoreMatchers.`is`("foo"))
    }
}