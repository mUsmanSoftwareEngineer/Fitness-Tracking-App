package com.home.workout.common.view

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton
import com.home.workout.stretching.utils.Utils


class CMButtonView : AppCompatButton {

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context) : super(context) {
        init()
    }


    fun init() {
        try {
            typeface = Utils.getMedium(context)
        } catch (e: Exception) {
        }

    }
}