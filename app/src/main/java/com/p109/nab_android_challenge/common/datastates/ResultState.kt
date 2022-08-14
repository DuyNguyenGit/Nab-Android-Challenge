package com.p109.nab_android_challenge.common.datastates

/**
 * This data class is being considered as wrapper class of the data in which it contain more status of the data
 * such as success, error or loading state. Those state will be observed by views(like fragments) to
 * determine state of UI.
 */
data class ResultState<out T>(val status: Status, val data: T?, val apiError: ApiError?) {
    companion object {
        fun <T> success(data: T?): ResultState<T> {
            return ResultState(Status.SUCCESS, data, null)
        }

        fun <T> error(apiError: ApiError?, data: T?): ResultState<T> {
            return ResultState(Status.ERROR, data, apiError)
        }

        fun <T> loading(data: T?): ResultState<T> {
            return ResultState(Status.LOADING, data, null)
        }
    }
}
