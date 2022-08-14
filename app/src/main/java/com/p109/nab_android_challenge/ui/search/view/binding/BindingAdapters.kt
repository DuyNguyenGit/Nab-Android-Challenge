/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.p109.nab_android_challenge.ui.search.view.binding

import androidx.databinding.BindingAdapter
import android.view.View
import android.widget.TextView
import com.p109.nab_android_challenge.R
import com.p109.nab_android_challenge.common.datastates.ApiError
import com.p109.nab_android_challenge.common.datastates.ResultState
import com.p109.nab_android_challenge.common.datastates.Status
import com.google.android.material.textfield.TextInputLayout

object BindingAdapters {
    @JvmStatic
    @BindingAdapter("visibleGone")
    fun showHide(view: View, show: Boolean) {
        view.visibility = if (show) View.VISIBLE else View.GONE
    }

    @JvmStatic
    @BindingAdapter("visibleGoneError")
    fun <T> showHideError(view: View, result: ResultState<T>?) {
        view.visibility = if (result?.status == Status.ERROR
            && (result.data == null || (result.data as List<*>).isEmpty())
        ) {
            View.VISIBLE
        } else View.GONE
    }

    @JvmStatic
    @BindingAdapter("textError")
    fun <T> textError(view: TextView, result: ResultState<T>?) {
        view.text = when (result?.apiError?.cod) {
            ApiError.API_ERROR_401 -> view.context.getString(R.string.text_error_authorization)
            ApiError.API_ERROR_404 -> view.context.getString(R.string.text_error_not_found)
            else -> view.context.getString(R.string.unknown_error)
        }
    }

    @JvmStatic
    @BindingAdapter("textInputError")
    fun showTextInputError(textInputLayout: TextInputLayout, input: String?) {
        val context = textInputLayout.context
        val errorMessage = if (input == null) null else if (input.isEmpty())
            context.getString(R.string.text_input_error_empty)
        else if (input.length < 3) {
            context.getString(R.string.text_input_error_too_short)
        } else {
            null
        }

        textInputLayout.error = errorMessage
    }
}
