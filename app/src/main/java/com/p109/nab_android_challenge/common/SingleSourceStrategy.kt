package com.p109.nab_android_challenge.common

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.p109.nab_android_challenge.common.datastates.ApiError
import com.p109.nab_android_challenge.common.datastates.ResultState
import com.p109.nab_android_challenge.data.search.remote.model.ApiEmptyResponse
import com.p109.nab_android_challenge.data.search.remote.model.ApiErrorResponse
import com.p109.nab_android_challenge.data.search.remote.model.ApiResponse
import com.p109.nab_android_challenge.data.search.remote.model.ApiSuccessResponse
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * This class comply to the Single source of true strategy. The main purpose of this class is ensure
 * the local database be always single source of data. In addtion to, this control the data sources, ensure
 * that data is cached in local database(used for offlined situation).
 */
abstract class SingleSourceStrategy<ResultType, RequestType> constructor(
    private val contextProviders: ContextProviders
) {

    private val result = MediatorLiveData<ResultState<ResultType>>()

    init {
        result.value = ResultState.loading(null)
        val dbSource = this.loadFromDb()
        result.addSource(dbSource) { data ->
            result.removeSource(dbSource)
            if (shouldFetch(data)) {
                fetchFromNetwork(dbSource)
            } else {
                result.addSource(dbSource) { newData ->
                    setValue(ResultState.success(newData))
                }
            }
        }
    }

    private fun setValue(newValue: ResultState<ResultType>) {
        if (result.value != newValue) {
            result.postValue(newValue)
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun fetchFromNetwork(dbSource: LiveData<ResultType>) {
        Log.d("NetworkBoundResource", "fetchFromNetwork: >>>start fetching from network.")
        val apiResponse = createCall()
        result.addSource(dbSource) { newData ->
            setValue(ResultState.loading(newData))
        }

        result.addSource(apiResponse) { response ->
            result.removeSource(apiResponse)
            result.removeSource(dbSource)
            when (response) {
                is ApiSuccessResponse -> {
                    GlobalScope.launch(contextProviders.IO) {
                        deleteOldDailyWeatherWithCurrentCity()
                        saveCallResult(processResponse(response))
                        GlobalScope.launch(contextProviders.Main) {
                            result.addSource(loadFromDb()) { newData ->
                                setValue(ResultState.success((newData)))
                            }
                        }
                    }
                }
                is ApiEmptyResponse -> {
                    GlobalScope.launch(contextProviders.Main) {
                        result.addSource(loadFromDb()) { newData ->
                            setValue(ResultState.success(newData))
                        }
                    }
                }
                is ApiErrorResponse -> {
                    onFetchFailed()
                    result.addSource(dbSource) { newData ->
                        setValue(
                            ResultState.error(
                                ApiError.parseMsgToError(response.errorMessage),
                                newData
                            )
                        )
                    }
                }
            }
        }
    }

    protected open fun onFetchFailed() {}

    fun asLiveData() = result as LiveData<ResultState<ResultType>>

    abstract fun saveCallResult(item: RequestType)

    abstract fun deleteOldDailyWeatherWithCurrentCity()

    protected fun processResponse(response: ApiSuccessResponse<RequestType>) = response.body

    abstract fun createCall(): LiveData<ApiResponse<RequestType>>

    protected abstract fun shouldFetch(data: ResultType?): Boolean

    protected abstract fun loadFromDb(): LiveData<ResultType>

}