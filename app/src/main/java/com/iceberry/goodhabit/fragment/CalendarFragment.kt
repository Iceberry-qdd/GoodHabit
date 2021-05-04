package com.iceberry.goodhabit.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.haibin.calendarview.Calendar
import com.haibin.calendarview.CalendarView
import com.iceberry.goodhabit.R
import com.iceberry.goodhabit.databinding.FragmentCalendarBinding
import com.iceberry.goodhabit.viewModel.FragmentViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [CalenderFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CalenderFragment : Fragment(),
    CalendarView.OnCalendarSelectListener,
    CalendarView.OnYearChangeListener,
    View.OnClickListener {

    companion object {
        fun newInstance() = CalenderFragment()
    }

    private var _viewBinding: FragmentCalendarBinding? = null
    private val viewBinding get() = _viewBinding!!

    lateinit var mTextMonthDay: TextView
    lateinit var mTextYear: TextView
    lateinit var mTextLunar: TextView

    //lateinit var mTextCurrentDay: ImageView
    lateinit var mCalendarView: CalendarView
    lateinit var mRelativeTool: ConstraintLayout
    private var mYear = 0

    private val viewModel: FragmentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        _viewBinding = FragmentCalendarBinding.inflate(inflater, container, false)

        return viewBinding.root
    }

    /**
     * Called when the fragment's activity has been created and this
     * fragment's view hierarchy instantiated.  It can be used to do final
     * initialization once these pieces are in place, such as retrieving
     * views or restoring state.  It is also useful for fragments that use
     * [.setRetainInstance] to retain their instance,
     * as this callback tells the fragment when it is fully associated with
     * the new activity instance.  This is called after [.onCreateView]
     * and before [.onViewStateRestored].
     *
     * @param savedInstanceState If the fragment is being re-created from
     * a previous saved state, this is the state.
     */
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mTextMonthDay = viewBinding.tvMonthDay
        mTextYear = viewBinding.tvYear
        mTextLunar = viewBinding.tvLunar
        mRelativeTool = viewBinding.rlTool
        mCalendarView = viewBinding.calendarView

        mTextLunar.visibility = View.GONE
        mTextYear.visibility = View.GONE
        //mTextCurrentDay =viewBinding.tvCurrentDay
        mCalendarView.setSchemeDate(viewModel.getSchemeDates())

        mTextMonthDay.setOnClickListener {
            mCalendarView.showYearSelectLayout(mYear)

            mTextMonthDay.text = "${mYear}年"
        }
        viewBinding.flCurrent.setOnClickListener {
            mCalendarView.scrollToCurrent(true)
        }
        mCalendarView.apply {
            setOnCalendarSelectListener(this@CalenderFragment)
            setOnYearChangeListener(this@CalenderFragment)
        }
        mTextYear.text = mCalendarView.curYear.toString()
        mYear = mCalendarView.curYear
        mTextMonthDay.text =
            "${mCalendarView.curYear}年${mCalendarView.curMonth}月${mCalendarView.curDay}日"
        mTextLunar.text = "今日"
        //mTextCurrentDay.setOnClickListener { mCalendarView.scrollToCurrent() }
        //mTextCurrentDay.text=mCalendarView.curDay.toString()
        //calendarView=view.findViewById(R.id.calendarView)

        viewModel.weekStartDay.observe(viewLifecycleOwner, {
            when (it) {
                "MON" -> {
                    mCalendarView.setWeekStarWithMon()
                }
                "SAT" -> {
                    mCalendarView.setWeekStarWithSat()
                }
                "SUN" -> {
                    mCalendarView.setWeekStarWithSun()
                }
            }
        })
    }

    /**
     * 超出范围越界
     *
     * @param calendar calendar
     */
    override fun onCalendarOutOfRange(calendar: Calendar?) {

    }

    /**
     * 日期选择事件
     *
     * @param calendar calendar
     * @param isClick  isClick
     */
    override fun onCalendarSelect(calendar: Calendar, isClick: Boolean) {
        mTextMonthDay.text = "${calendar.year}年${calendar.month}月${calendar.day}日"

        if (viewModel.allowReissue.value == false) return
        if (calendar.timeInMillis > System.currentTimeMillis() && isClick) {
            Toast.makeText(requireContext(), "超过范围", Toast.LENGTH_LONG).show()
            return
        }
        if (!calendar.hasScheme() && isClick) {
            MaterialAlertDialogBuilder(requireContext(), R.style.ThemeOverlay_MaterialComponents_Dialog_Alert)
                .setTitle("确定补签")
                .setMessage("您确定要补签吗？")
                .setNegativeButton("取消"){_,_->}
                .setPositiveButton("确定"){_,_->
                    viewModel.addSchemeDate(calendar)
                    mCalendarView.setSchemeDate(viewModel.getSchemeDates())
                }.show()
            return
        }
    }

    override fun onYearChange(year: Int) {
        mTextMonthDay.text = "${year}年"
    }


    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    override fun onClick(v: View?) {
    }

    override fun onPause() {
        viewModel.saveSchemeDates()
        super.onPause()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _viewBinding = null
    }
}