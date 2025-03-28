package com.home.workout.stretching

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.text.style.UnderlineSpan
import android.view.View
import androidx.databinding.DataBindingUtil
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.home.workout.R
import com.home.workout.databinding.ActivityIntroductionBinding
import com.home.workout.stretching.interfaces.CallbackListener
import com.home.workout.stretching.objects.HomePlanTableClass
import com.home.workout.stretching.utils.Utils


class IntroductionActivity : BaseActivity(), CallbackListener {

    var binding: ActivityIntroductionBinding? = null
    var workoutPlanData: HomePlanTableClass? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_introduction)

        initIntentParam()
        init()
    }

    private fun initIntentParam() {
        try {
            if (intent.extras != null) {
                if (intent.extras!!.containsKey("workoutPlanData")) {
                    val str = intent.getStringExtra("workoutPlanData")
                    workoutPlanData = Gson().fromJson(str, object :
                        TypeToken<HomePlanTableClass>() {}.type)!!
                }
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun init() {
        binding!!.handler = ClickHandler()

        fillData()

    }


    private fun fillData() {
        if (workoutPlanData != null) {

            binding!!.tvIntroductionDes.text = workoutPlanData!!.introduction
            binding!!.tvTestDes.text = workoutPlanData!!.planTestDes
            binding!!.tvTitle.text = workoutPlanData!!.planName!!.substringBefore("correction") + " " + getString(R.string.test)
            binding!!.tvTest.text = workoutPlanData!!.planName!!.substringBefore("correction")

            binding!!.imgTest.setImageResource(Utils.getDrawableId(workoutPlanData!!.planImage!!.replace("cover_","img_"), this))

            val str ="This is simple self-test. For more information, please check on Wikipedia, or consult your doctor."
            val spannable: Spannable = SpannableString(str)

            val indexTermsStart: Int = str.indexOf("Wikipedia")
            val indexTermsEnd = indexTermsStart + 9
            spannable.setSpan(UnderlineSpan(), indexTermsStart, indexTermsEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            spannable.setSpan(object : ClickableSpan() {
                override fun onClick(widget: View) {

                    var url = ""
                    if(workoutPlanData!!.planName!!.equals("Knock knee correction"))
                    {
                        url = "https://en.m.wikipedia.org/wiki/Genu_valgum"
                    }else{
                        url ="https://en.m.wikipedia.org/wiki/Genu_varum"
                    }
                    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    startActivity(browserIntent)

                }
            }, indexTermsStart, indexTermsEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            spannable.setSpan(ForegroundColorSpan(Color.BLUE), indexTermsStart, indexTermsEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            binding!!.tvWikiPedia.text = spannable
            binding!!.tvWikiPedia.setClickable(true)
            binding!!.tvWikiPedia.setMovementMethod(LinkMovementMethod.getInstance())
        }
    }

    override fun onResume() {
        openInternetDialog(this,false)
        super.onResume()
    }


    inner class ClickHandler {

        fun onCancelClick() {
            finish()
        }

    }

    override fun onSuccess() {

    }

    override fun onCancel() {

    }

    override fun onRetry() {

    }


}
