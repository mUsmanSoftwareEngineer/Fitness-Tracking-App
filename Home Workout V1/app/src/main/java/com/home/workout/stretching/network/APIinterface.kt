package com.home.workout.stretching.network

import com.home.workout.stretching.viewmodel.AdsIdDataResponse
import retrofit2.Call
import retrofit2.http.GET


interface APIinterface {

    @GET("advertisement/17")
    fun getAdvertisementID(): Call<ArrayList<AdsIdDataResponse>>
}