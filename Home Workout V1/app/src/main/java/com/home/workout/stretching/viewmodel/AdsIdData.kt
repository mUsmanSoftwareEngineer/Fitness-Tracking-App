package com.home.workout.stretching.viewmodel

import androidx.lifecycle.MutableLiveData
import com.home.workout.stretching.network.APIClient
import com.home.workout.stretching.datasource.CommonRepository
import com.home.workout.stretching.network.APIinterface
import com.home.workout.stretching.viewmodel.AdsIdDataResponse


class AdsIdData {

    constructor()

    fun getAdId(): MutableLiveData<AdsIdDataResponse> {
        val apInterface: APIinterface = APIClient.benzatineInfotech().create(APIinterface::class.java)
        val commanrepo = CommonRepository(apInterface)
        return commanrepo.getAdsId()
    }
}