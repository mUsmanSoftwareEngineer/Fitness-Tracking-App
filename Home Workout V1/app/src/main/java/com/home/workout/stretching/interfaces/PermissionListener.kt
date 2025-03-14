package com.home.workout.stretching.interfaces

interface PermissionListener {

    fun onPermissionClick()

    fun onPermissionAllow(isAllow: Boolean)
}