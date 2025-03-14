package com.home.workout.stretching.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.home.workout.stretching.objects.BaseModel
import com.home.workout.stretching.objects.ResultModel
import com.home.workout.stretching.utils.Constant

abstract class BaseViewModel(application: Application) : AndroidViewModel(application) {


    fun getError(): BaseModel {
        val baseModel = BaseModel()
        baseModel.code = Constant.STATUS_ERROR_CODE
        return baseModel
    }

    fun getErrorResult(): ResultModel {
        val resultModel = ResultModel()
        resultModel.code = Constant.STATUS_ERROR_CODE
        return resultModel
    }

    fun getEmptyResult(): ResultModel {
        val resultModel = ResultModel()
        resultModel.code = Constant.STATUS_SUCCESS_EMPTY_LIST_CODE
        return resultModel
    }

    fun getExistsResult(): ResultModel {
        val resultModel = ResultModel()
        resultModel.code = Constant.STATUS_SUCCESS_EXISTS_CODE
        return resultModel
    }

    fun getNotExistsResult(): ResultModel {
        val resultModel = ResultModel()
        resultModel.code = Constant.STATUS_SUCCESS_NOT_EXISTS_CODE
        return resultModel
    }

    fun getSuccessResult(): ResultModel {
        return ResultModel(Constant.STATUS_SUCCESS_CODE)
    }



}