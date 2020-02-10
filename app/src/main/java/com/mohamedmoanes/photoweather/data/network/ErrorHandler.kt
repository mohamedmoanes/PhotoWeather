package com.mohamedmoanes.photoweather.data.network

import android.util.Log
import com.mohamedmoanes.photoweather.R
import com.mohamedmoanes.photoweather.app.PhotoWeatherApplication
import com.mohamedmoanes.photoweather.ui.base.ResultListener
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException
const val TAG ="NetWork"
private fun responseErrorHandler(response: String, responsecode: Int): String {
    val context = PhotoWeatherApplication.appContext.applicationContext
    return when {
        responsecode < 500 -> {
            try {
                val responseObject = JSONObject(response)
                if (responseObject.has("data")) {
                    return responseObject.getString("data")
                }
                if (responseObject.has("errors")) {
                    val dataObject = responseObject.getJSONObject("errors")
                    if (dataObject.has("email"))
                        return dataObject.getJSONArray("email").getString(0)
                }
                responseObject.getString("message")
            } catch (e: Exception) {
                Log.e(TAG,e.message)

                if (null != e.message)
                    e.message!!
                else
                    context.getString(R.string.error)

            }
        }
        responsecode == 500 -> {
            context.getString(R.string.server_error)
        }
        else -> {
            context.getString(R.string.error)
        }
    }
}

private fun failureHandler(t: Throwable): String {
    val context = PhotoWeatherApplication.appContext.applicationContext
    return if (t is IOException) {
        context.getString(R.string.no_internet)
    } else {
       Log.e(TAG,t.message)
        context.getString(R.string.error)
    }
}

fun errorHandler(throwable: Throwable, resultListener: ResultListener<*>) {
    try {
        if (throwable is HttpException) {
            val response = throwable.response()!!.errorBody()!!.string()
            resultListener.onError(responseErrorHandler(response, throwable.code()))
        } else resultListener.onFailure(failureHandler(throwable))
    } catch (e: Exception) {
        Log.e(TAG,e.message)
    }
}