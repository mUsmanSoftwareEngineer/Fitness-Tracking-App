package com.home.workout.stretching

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.appbar.AppBarLayout
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.home.workout.R
import com.home.workout.databinding.ActivityExerciseListBinding
import com.home.workout.stretching.adapter.WorkoutListAdapter
import com.home.workout.stretching.interfaces.AdsCallback
import com.home.workout.stretching.interfaces.CallbackListener
import com.home.workout.stretching.objects.HomePlanTableClass
import com.home.workout.stretching.utils.AdUtils
import com.home.workout.stretching.utils.Constant
import com.home.workout.stretching.utils.Utils


class ExercisesListActivity : BaseActivity(), CallbackListener, AdsCallback {

    var binding: ActivityExerciseListBinding? = null
    var workoutListAdapter: WorkoutListAdapter? = null
    var workoutPlanData: HomePlanTableClass? = null
    var isCheck:Boolean=false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_exercise_list)

//        AdUtils.loadBannerAd(binding!!.adView,this)
//        AdUtils.loadBannerGoogleAd(this,binding!!.llAdView,Constant.BANNER_TYPE)
//        showUnlockTrainingDialog(this)

        if (Utils.getPref(this, Constant.AD_TYPE_FB_GOOGLE, "") == Constant.AD_GOOGLE) {
            AdUtils.loadGoogleBannerAd(this, binding!!.llAdView, Constant.BANNER_TYPE)
            binding!!.llAdViewFacebook.visibility=View.GONE
            binding!!.llAdView.visibility=View.VISIBLE
        }else if (Utils.getPref(this, Constant.AD_TYPE_FB_GOOGLE, "") == Constant.AD_FACEBOOK) {
            AdUtils.loadFacebookBannerAd(this,binding!!.llAdViewFacebook)
            binding!!.llAdViewFacebook.visibility=View.VISIBLE
            binding!!.llAdView.visibility=View.GONE
        }else{
            binding!!.llAdView.visibility=View.GONE
            binding!!.llAdViewFacebook.visibility=View.GONE
        }

        if (Utils.isPurchased(this)) {
            binding!!.llAdView.visibility=View.GONE
            binding!!.llAdViewFacebook.visibility = View.GONE
        }

        initIntentParam()
        init()


        if (Utils.getPref(this,Constant.AD_TYPE_FB_GOOGLE,"") == Constant.AD_GOOGLE) {
            AdUtils.GooglebeforloadAd(this)
        } else if (Utils.getPref(this,Constant.AD_TYPE_FB_GOOGLE,"") == Constant.AD_FACEBOOK) {
            AdUtils.FacebookbeforeloadFullAd(this)
        }
    }

    private fun initIntentParam() {
        try {
            if (intent.extras != null) {
                if (intent.extras!!.containsKey("workoutPlanData")) {
                    val str = intent.getStringExtra("workoutPlanData")
                    workoutPlanData = Gson().fromJson(str, object :
                        TypeToken<HomePlanTableClass>() {}.type)!!
                }
                if (intent.extras!!.containsKey(Constant.IS_PURCHASE)) {
                    isCheck = intent.getBooleanExtra(Constant.IS_PURCHASE, false)
                    if (isCheck) {
                        showUnlockTrainingDialog(this)
                    }
                }
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun init() {
        binding!!.handler = ClickHandler()

        workoutListAdapter = WorkoutListAdapter(this)
        binding!!.rvWorkOuts.layoutManager = LinearLayoutManager(this)
        binding!!.rvWorkOuts.setAdapter(workoutListAdapter)

        workoutListAdapter!!.setEventListener(object : WorkoutListAdapter.EventListener {
            override fun onItemClick(position: Int, view: View) {
                val item = workoutListAdapter!!.getItem(position)

                if (workoutPlanData!!.planType!!.equals(Constant.PlanTypeWorkouts) && workoutPlanData!!.planDays != Constant.PlanDaysYes) {
                    showChangeTimeDialog(item,
                        object : DialogInterface {
                            override fun dismiss() {
                                getExerciseData()
                            }

                            override fun cancel() {

                            }

                        })
                } else {
                    showExcDetailDialog(workoutListAdapter!!.data,position)
                }

            }

        })

        var isShow = true
        var scrollRange = -1
        binding!!.appbar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { barLayout, verticalOffset ->
            if (scrollRange == -1) {
                scrollRange = barLayout?.totalScrollRange!!
            }
            if (scrollRange + verticalOffset == 0) {
                binding!!.llTopTitle.visibility = View.VISIBLE
                isShow = true
            } else if (isShow) {
                binding!!.llTopTitle.visibility =
                    View.GONE //careful there should a space between double quote otherwise it wont work
                isShow = false
            }
        })

        fillData()
        getExerciseData()
    }

    private fun getExerciseData() {
        if (workoutPlanData!!.planDays == Constant.PlanDaysYes) {
            val dayId = intent.getStringExtra(Constant.extra_day_id)
            if (dayId != null) {
                workoutListAdapter!!.addAll(dbHelper.getDayExList(dayId))
            }

        } else {
            workoutListAdapter!!.addAll(dbHelper.getHomeDetailExList(workoutPlanData!!.planId!!))
            Log.e("TAG", "getExerciseData: ::::  "+workoutPlanData!!.planId +"  "+Gson().toJson(dbHelper.getHomeDetailExList(workoutPlanData!!.planId!!)))

        }
    }


    private fun fillData() {
        if (workoutPlanData != null) {

            binding!!.imgCover.setImageResource(
                Utils.getDrawableId(
                    workoutPlanData!!.planImage,
                    this
                )
            )

            if (workoutPlanData!!.planDays == Constant.PlanDaysYes) {
                binding!!.tvTitleText.text = "Day " + intent.getStringExtra("day_name")
                binding!!.tvTitle.text = "Day " + intent.getStringExtra("day_name")
                binding!!.tvShortDes.text = workoutPlanData!!.planName
                Log.e("TAG", "fillData:11111111 "+ workoutPlanData!!.planName)
            } else {
                binding!!.tvTitleText.text = workoutPlanData!!.planName
                binding!!.tvTitle.text = workoutPlanData!!.planName

                if (workoutPlanData!!.shortDes.isNullOrEmpty().not()) {
                    binding!!.tvShortDes.text = workoutPlanData!!.shortDes
                    Log.e("TAG", "fillData:2222222222 "+ workoutPlanData!!.shortDes)
                }
            }

            if (workoutPlanData!!.introduction.isNullOrEmpty().not()) {


                if (workoutPlanData!!.planTestDes.isNullOrEmpty().not()) {
                    binding!!.tvTestName.text = workoutPlanData!!.planName!!.substringBefore("correction") + " " + getString(
                            R.string.test
                        )
                    binding!!.tvIntroductionDes.ellipsize = TextUtils.TruncateAt.END
                    binding!!.tvIntroductionDes.maxLines = 3
                } else {
                    binding!!.llTest.visibility = View.GONE

                }
                binding!!.tvIntroductionDes.text = workoutPlanData!!.introduction

            } else {
                binding!!.llIntroduction.visibility = View.GONE
            }

            if (workoutPlanData!!.planType!!.equals(Constant.PlanTypeWorkouts) && workoutPlanData!!.planDays != Constant.PlanDaysYes) {
                binding!!.imgEdit.visibility = View.VISIBLE
            } else {
                binding!!.imgEdit.visibility = View.GONE
            }

            binding!!.tvWorkoutTime.text =
                workoutPlanData!!.planMinutes + " " + getString(R.string.mins)

            if(workoutPlanData!!.planWorkouts.equals("0") && workoutPlanData!!.planLvl.isNullOrEmpty().not())
                binding!!.tvTotalWorkout.text = workoutPlanData!!.planLvl
            else
                binding!!.tvTotalWorkout.text = workoutPlanData!!.planWorkouts + " " + getString(R.string.workouts)



        }
    }

    override fun onResume() {
        openInternetDialog(this,false)
        super.onResume()
    }

    var adClickCount: Int = 1
    inner class ClickHandler {

        fun onEditWorkoutsClick() {
            val intent = Intent(this@ExercisesListActivity, EditPlanActivity::class.java)
            intent.putExtra("workoutPlanData", Gson().toJson(workoutPlanData))
            startActivityForResult(intent, 7979)
        }

        fun onStartClick() {

            if (isCheck){
                startExerciseActivity()
            }else {
                if (Utils.getPref(this@ExercisesListActivity, Constant.START_BTN_COUNT, 1) == 1) {
                    if (Utils.getPref(
                            this@ExercisesListActivity,
                            Constant.STATUS_ENABLE_DISABLE,
                            ""
                        ) == Constant.ENABLE
                    ) {
                        when (Utils.getPref(
                            this@ExercisesListActivity,
                            Constant.AD_TYPE_FB_GOOGLE,
                            ""
                        )) {
                            Constant.AD_GOOGLE -> {
                                AdUtils.showInterstitialAdsGoogle(
                                    this@ExercisesListActivity,
                                    this@ExercisesListActivity
                                )
                            }
                            Constant.AD_FACEBOOK -> {
                                AdUtils.showInterstitialAdsFacebook(
                                    this@ExercisesListActivity,
                                    this@ExercisesListActivity
                                )
                            }
                            else -> {
                                startExerciseActivity()
                            }
                        }
                        Utils.setPref(this@ExercisesListActivity, Constant.START_BTN_COUNT, 0)
                    } else {
                        startExerciseActivity()
                    }
                } else {
                    if (adClickCount == 1) {
                        Utils.setPref(this@ExercisesListActivity, Constant.START_BTN_COUNT, 1)
                    }
                    startExerciseActivity()
                }
            }
        }

        fun onIntroductionClick() {
            if (binding!!.llIntroductionDes.visibility == View.VISIBLE) {
                binding!!.llIntroductionDes.visibility = View.GONE
            } else {
                binding!!.llIntroductionDes.visibility = View.VISIBLE
            }
        }

        fun onBackClick() {
            finish()
        }

        fun onTestClick() {
            val i = Intent(this@ExercisesListActivity, IntroductionActivity::class.java)
            i.putExtra("workoutPlanData",Gson().toJson(workoutPlanData))
            startActivity(i)
        }
    }

    fun startExerciseActivity(){
        val intent = Intent(this@ExercisesListActivity, PerformWorkOutActivity::class.java)
        intent.putExtra("workoutPlanData", Gson().toJson(workoutPlanData))
        intent.putExtra("ExcList", Gson().toJson(workoutListAdapter!!.data))
        startActivity(intent)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 7979 && resultCode == Activity.RESULT_OK) {
            getExerciseData()
        }
    }

    override fun onSuccess() {

    }

    override fun onCancel() {

    }

    override fun onRetry() {

    }

    override fun startNextScreenAfterAd() {
        startExerciseActivity()
    }

}
