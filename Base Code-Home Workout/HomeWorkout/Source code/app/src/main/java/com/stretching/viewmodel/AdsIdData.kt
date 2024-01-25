package com.stretching.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.admision.network.APIClient
import com.admision.network.APIinterface
import com.stretching.datasource.CommonRepository


class AdsIdData {

    constructor()

    fun getAdId(): MutableLiveData<AdsIdDataResponse> {
        val apInterface: APIinterface = APIClient.benzatineInfotech().create(APIinterface::class.java)
        val commanrepo = CommonRepository(apInterface)
        return commanrepo.getAdsId()
    }
}