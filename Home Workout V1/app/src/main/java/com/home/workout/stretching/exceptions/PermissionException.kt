package com.home.workout.stretching.exceptions

import com.home.workout.stretching.exceptions.BaseException
import com.home.workout.stretching.utils.Debug

class PermissionException : BaseException() {

    override fun printStackTrace() {
        super.printStackTrace()
        Debug.e("Permission","Permission denied" )
    }
}