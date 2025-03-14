package com.home.workout.stretching.utils

import android.content.Context
import com.home.workout.R
import com.home.workout.stretching.exceptions.networks.HttpRequestException
import com.home.workout.stretching.exceptions.networks.NoInternetException
import com.home.workout.stretching.interfaces.CallbackListener
import com.home.workout.stretching.utils.ErrorAlertDialog

object AlertDialogHelper {

    fun showNoInternetDialog(context: Context,callbackListener: CallbackListener) {
        val exception = NoInternetException()
        ErrorAlertDialog(context).setTitle(exception.title)
                .setMessage(exception.errMessage)
                .setNegativeButton(context.getString(R.string.cancel))
                .setPositiveButton(context.getString(R.string.retry))
                .setOnButtonClickListener(object : ErrorAlertDialog.DialogButtonClick{
                    override fun onPositiveClick() {
                        callbackListener.onRetry()
                    }

                    override fun onNegativeClick() {
                        callbackListener.onCancel()
                    }
                }).show()
    }

    fun showHttpExceptionDialog(context: Context) {
        val exception = HttpRequestException()
        ErrorAlertDialog(context).setTitle(exception.title)
                .setMessage(exception.errMessage)
                .setPositiveButton(context.getString(R.string.btn_ok))
                .setOnButtonClickListener(object : ErrorAlertDialog.DialogButtonClick{
                    override fun onPositiveClick() {
                    }

                    override fun onNegativeClick() {
                    }
                }).show()
    }
}