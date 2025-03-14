package com.home.workout.stretching.exceptions.networks

import com.home.workout.stretching.exceptions.networks.NetworkException
import com.home.workout.stretching.utils.Debug

class NoInternetException : NetworkException() {

    override var errMessage: String = "No Internet Available"

    override var title: String = "Alert"

    override fun printStackTrace() {
        super.printStackTrace()
        Debug.e("NoInternet","No Internet Connection")
    }

    override fun getLocalizedMessage(): String {
        return super.getLocalizedMessage()
    }
}