package com.admision.network

import com.stretching.objects.*
import com.stretching.viewmodel.AdsIdDataResponse
import com.stretching.viewmodel.LoginDataModel
import com.stretching.viewmodel.UserListDataModel
import retrofit2.Call
import retrofit2.http.*


interface APIinterface {

    @GET("advertisement/17")
    fun getAdvertisementID(): Call<ArrayList<AdsIdDataResponse>>
}