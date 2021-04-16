package com.iceberry.goodhabit

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.haibin.calendarview.Calendar
import com.haibin.calendarview.CalendarView
import com.iceberry.goodhabit.databinding.FragmentCalendarBinding

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

    private val viewModel: CalendarFragmentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        _viewBinding = FragmentCalendarBinding.inflate(inflater, container, false)
        val view = viewBinding.root
        //context.obtainStyledAttributes(R.styleable.CalendarView_max_year)
        //val dataBinding:FragmentCalendarBinding=DataBindingUtil.setContentView( requireActivity(),R.layout.fragment_calendar)
        //viewBinding.currentYear=java.util.Calendar.YEAR.toString()
        //inflater.inflate(R.layout.fragment_calendar, container, false)
        //setStatusBarDarkMode();
        mTextMonthDay = viewBinding.tvMonthDay
        mTextYear = viewBinding.tvYear
        mTextLunar = viewBinding.tvLunar
        mRelativeTool = viewBinding.rlTool
        mCalendarView = viewBinding.calendarView
        //mTextCurrentDay =viewBinding.tvCurrentDay

        mTextMonthDay.setOnClickListener {
            mCalendarView.showYearSelectLayout(mYear)
            mTextLunar.visibility = View.GONE
            mTextYear.visibility = View.GONE
            mTextMonthDay.text = mYear.toString()
        }
        viewBinding.flCurrent.setOnClickListener {
            mCalendarView.scrollToCurrent()
        }
        mCalendarView.apply {
            setOnCalendarSelectListener(this@CalenderFragment)
            setOnYearChangeListener(this@CalenderFragment)
        }
        mTextYear.text = mCalendarView.curYear.toString()
        mYear = mCalendarView.curYear
        mTextMonthDay.text = "${mCalendarView.curMonth}月${mCalendarView.curDay}日"
        mTextLunar.text = "今日"
        //mTextCurrentDay.setOnClickListener { mCalendarView.scrollToCurrent() }
        //mTextCurrentDay.text=mCalendarView.curDay.toString()

        //calendarView=view.findViewById(R.id.calendarView)
        return view
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

        //viewModel = ViewModelProvider(this).get(CalendarFragmentModel::class.java)

        //val mSchemeDates= mutableMapOf<String,Calendar>(Pair(getSchemeCalendar(2021,3,5,"签").toString(),getSchemeCalendar(2021,3,5,"签")))
        //mSchemeDates.plus(Pair(getSchemeCalendar(2021,3,5,"签").toString(),getSchemeCalendar(2021,3,5,"签")))
        //mSchemeDates[getSchemeCalendar(2021,3,7,"签").toString()] = getSchemeCalendar(2021,3,7,"签")
        //mCalendarView.setSchemeDate(mSchemeDates)
        //viewModel
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
        mTextLunar.visibility = View.VISIBLE
        mTextYear.visibility = View.VISIBLE
        mTextMonthDay.text = "${calendar.month}月${calendar.day}日"
        mTextYear.text = calendar.year.toString()
        mTextLunar.text = calendar.lunar.toString()
        mYear = calendar.year

        Log.d("TAG", "${calendar.timeInMillis}")
        if (calendar.timeInMillis > System.currentTimeMillis()) {
            Toast.makeText(requireContext(), "超过范围", Toast.LENGTH_LONG).show()
            return
        }
        if (!calendar.hasScheme() && isClick) {
            viewModel.addScheme(calendar)
            mCalendarView.setSchemeDate(viewModel.getScheme())
        }

    }

    override fun onYearChange(year: Int) {
        mTextMonthDay.text = year.toString()
    }


    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    override fun onClick(v: View?) {
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _viewBinding = null
    }
}