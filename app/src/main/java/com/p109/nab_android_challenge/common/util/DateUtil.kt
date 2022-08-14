package com.p109.nab_android_challenge.common.util

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*


class DateUtil {
    companion object {
        @SuppressLint("SimpleDateFormat")
        fun convertFromTimeStamp(timestampInMillis: Long): String {
            try {
                val sdf = SimpleDateFormat("EEE, dd MMM yyyy")
                val netDate = Date(timestampInMillis)
                return sdf.format(netDate)
            } catch (e: Exception) {
                return "unknown!"
            }
        }
    }
}