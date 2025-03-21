package com.home.workout.stretching.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.bignerdranch.expandablerecyclerview.ChildViewHolder
import com.bignerdranch.expandablerecyclerview.ExpandableRecyclerAdapter
import com.bignerdranch.expandablerecyclerview.ParentViewHolder
import com.bignerdranch.expandablerecyclerview.model.Parent
import com.home.workout.R
import com.home.workout.databinding.ItemHistoryChildBinding
import com.home.workout.databinding.ItemHistoryParentBinding
import com.home.workout.stretching.objects.HistoryDetailsClass
import com.home.workout.stretching.objects.HistoryWeekDataClass
import com.home.workout.stretching.utils.Constant
import com.home.workout.stretching.utils.Utils
import java.lang.Exception
import java.util.*
import kotlin.Comparator


class HistoryExpandableAdapter(private var context: Context?, groups: List<HistoryWeekDataClass?>) :
    ExpandableRecyclerAdapter<HistoryWeekDataClass, HistoryDetailsClass, HistoryExpandableAdapter.GrpViewHolder, HistoryExpandableAdapter.ItemViewHolder>(
        groups
    ) {

    private var mInflater: LayoutInflater? = null
    private var mEventListener: EventListener? = null
    private var isMultiSelect = false


    init {
        parentList.clear()
        parentList.addAll(groups)
        mInflater = LayoutInflater.from(context)
    }

    fun getAll(): List<HistoryWeekDataClass?>? {
        return parentList
    }

    fun getMenuItem(position: Int): HistoryWeekDataClass? {
        return getParentList().get(position)
    }

    fun getMenuSubItem(position: Int, child: Int): HistoryDetailsClass? {
        return getParentList().get(position).getChildList().get(child)
    }

    fun addAll(groups: List<HistoryWeekDataClass?>) {
        getParentList().clear()
        Collections.sort(groups, DescByWeekNumberComparator())
        getParentList().addAll(groups)
        notifyParentDataSetChanged(false)
    }

    inner class DescByWeekNumberComparator : Comparator<HistoryWeekDataClass?> {
        override fun compare(tra1: HistoryWeekDataClass?, tra2: HistoryWeekDataClass?): Int {
            try {
                var entryDate1 = tra1!!.weekNumber.toInt()
                var entryDate2 = tra2!!.weekNumber.toInt()
                return if (entryDate1 > entryDate2) -1 else 1
            } catch (e: Exception) {
                e.printStackTrace()
                return 1
            }

        }
    }

    fun setMultiSelect(multiSelect: Boolean) {
        isMultiSelect = multiSelect
    }


    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }

    override fun getItemCount(): Int {
        return super.getItemCount()
    }

    override fun onCreateParentViewHolder(
        parentViewGroup: ViewGroup,
        viewType: Int
    ): GrpViewHolder {
        val inflater = LayoutInflater.from(parentViewGroup.context)

        val rowSideMenuBinding = DataBindingUtil.inflate<ItemHistoryParentBinding>(
            inflater,
            R.layout.item_history_parent, parentViewGroup, false
        )

        return GrpViewHolder(rowSideMenuBinding)
    }

    override fun onCreateChildViewHolder(childViewGroup: ViewGroup, viewType: Int): ItemViewHolder {
        val inflater = LayoutInflater.from(childViewGroup.context)

        val rowSideMenuBinding = DataBindingUtil.inflate<ItemHistoryChildBinding>(
            inflater,
            R.layout.item_history_child, childViewGroup, false
        )

        return ItemViewHolder(rowSideMenuBinding)
    }

    override fun onBindParentViewHolder(
        parentViewHolder: GrpViewHolder, parentPosition: Int,
        recipe: HistoryWeekDataClass
    ) {
        parentViewHolder.parentBinding.tvWeek.text =
            Utils.parseTime(recipe.weekStart, Constant.DATE_FORMAT, "MMM dd").plus(" - ")
                .plus(Utils.parseTime(recipe.weekEnd, Constant.DATE_FORMAT, "MMM dd"))
        parentViewHolder.parentBinding.tvTotalWorkout.text = recipe.totWorkout.toString()
            .plus(" ${context!!.resources.getString(R.string.workouts)}")
        parentViewHolder.parentBinding.tvDuration.text = Utils.secToString(recipe.totTime, "MM:SS")
        parentViewHolder.parentBinding.tvCalorie.text =
            recipe.totKcal.toString().plus(" ${context!!.resources.getString(R.string.calorie)}")

        parentViewHolder.parentBinding.container.setOnClickListener {

        }

    }

    override fun onBindChildViewHolder(
        childHolder: ItemViewHolder,
        parentPosition: Int, childPosition: Int,
        ingredient: HistoryDetailsClass
    ) {

        if (ingredient.planDetail!!.planDays == Constant.PlanDaysYes) {
            childHolder.childBinding!!.tvName.text =
                ingredient!!.PlanName.plus(" - Day ").plus(ingredient!!.DayName)
        } else {
            childHolder.childBinding!!.tvName.text = ingredient!!.PlanName
        }

        childHolder.childBinding!!.imgPlanType.setImageResource(
            Utils.getDrawableId(
                ingredient.planDetail!!.planTypeImage, context!!
            )
        )
        childHolder.childBinding.tvDateTime.text =
            Utils.parseTime(ingredient.DateTime, Constant.DATE_TIME_24_FORMAT, "MMM dd, HH:mm a")
        childHolder.childBinding.tvDuration.text =
            Utils.secToString(ingredient.CompletionTime.toInt(), "MM:SS")
        childHolder.childBinding.tvCalorie.text = Utils.truncateUptoTwoDecimal(ingredient.BurnKcal)
            .plus(" ${context!!.resources.getString(R.string.calorie)}")

        childHolder.childBinding.container.setOnClickListener(View.OnClickListener {
            if (mEventListener != null) {
                mEventListener!!.OnMenuClick(parentPosition, childPosition)
            }
        })
    }

    fun getItem(position: Int): HistoryWeekDataClass? {
        return getParentList().get(position)
    }

    class GrpViewHolder(val parentBinding: ItemHistoryParentBinding) :
        ParentViewHolder<Parent<Any?>, Any?>(parentBinding.root) {
    }


    class ItemViewHolder(val childBinding: ItemHistoryChildBinding) :
        ChildViewHolder<Any?>(childBinding.root) {
    }


    interface EventListener {
        fun OnMenuClick(parentPosition: Int, childPosition: Int)
    }

    fun setEventListener(eventlistener: EventListener?) {
        mEventListener = eventlistener
    }
}