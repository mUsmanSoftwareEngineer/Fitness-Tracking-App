package com.home.workout.stretching.network

import okhttp3.ResponseBody


object RetrofitUtils {

    fun getResponseString(responseBody: ResponseBody?) : String{
        return responseBody!!.source().inputStream().bufferedReader().use { it.readText() }
    }
}