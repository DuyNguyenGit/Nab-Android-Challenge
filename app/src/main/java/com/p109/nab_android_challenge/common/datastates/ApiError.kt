package com.p109.nab_android_challenge.common.datastates

import com.google.gson.Gson

data class ApiError(
    val cod: String = "",
    val message: String = ""
) {
    companion object {
        const val API_ERROR_401 = "401"
        const val API_ERROR_404 = "404"
        val gson = Gson()
        fun parseMsgToError(msg: String): ApiError? = try {
            gson.fromJson(msg, ApiError::class.java)
        } catch (_: Exception) {
            null
        }
    }
}
