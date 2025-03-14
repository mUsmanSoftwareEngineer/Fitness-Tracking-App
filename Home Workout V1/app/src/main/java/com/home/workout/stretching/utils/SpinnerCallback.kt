package com.home.workout.stretching.utils

import com.home.workout.stretching.objects.Spinner
import java.util.*


interface SpinnerCallback {
    abstract fun onDone(list: ArrayList<Spinner>)
}