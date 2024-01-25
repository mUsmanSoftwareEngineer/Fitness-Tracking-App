package com.stretching.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.admision.network.APIClient
import com.admision.network.APIinterface
import com.stretching.datasource.CommonRepository

class LoginDataModel()  {


    var facilityId: Int? = null
    var userId: Int? = null
    var pin: Int? = null

   /* fun login(context: Context): MutableLiveData<LoginData?> {
        val apInterface: APIinterface = APIClient.newRequestRetrofit().create(APIinterface::class.java)
        val venueRepository = CommonRepository(apInterface)
        return venueRepository.login(this)
    }*/


}