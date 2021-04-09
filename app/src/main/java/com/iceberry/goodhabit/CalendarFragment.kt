package com.iceberry.goodhabit

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.haibin.calendarview.Calendar
import com.haibin.calendarview.CalendarLayout
import com.haibin.calendarview.CalendarView
import com.iceberry.goodhabit.databinding.FragmentCalendarBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CalenderFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CalenderFragment : Fragment(),
    CalendarView.OnCalendarSelectListener,
    CalendarView.OnYearChangeListener,
    View.OnClickListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var _binding:FragmentCalendarBinding?=null
    private val binding get() = _binding!!

    lateinit var mTextMonthDay: TextView
    lateinit var mTextYear: TextView
    lateinit var mTextLunar: TextView
    lateinit var mTextCurrentDay: TextView
    lateinit var mCalendarView: CalendarView
    lateinit var mRelativeTool: ConstraintLayout
    private var mYear = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        _binding= FragmentCalendarBinding.inflate(inflater,container,false)
        val view= binding.root
        //inflater.inflate(R.layout.fragment_calendar, container, false)
        //setStatusBarDarkMode();
        mTextMonthDay =binding.tvMonthDay
        mTextYear =binding.tvYear
        mTextLunar = binding.tvLunar
        mRelativeTool =binding.rlTool
        mCalendarView =binding.calendarView
        mTextCurrentDay =binding.tvCurrentDay

        mTextMonthDay.setOnClickListener {
            mCalendarView.showYearSelectLayout(mYear)
            mTextLunar.visibility=View.GONE
            mTextYear.visibility=View.GONE
            mTextMonthDay.text=mYear.toString()
        }
        binding.flCurrent.setOnClickListener {
            mCalendarView.scrollToCurrent()
        }
        mCalendarView.apply {
            setOnCalendarSelectListener(this@CalenderFragment)
            setOnYearChangeListener(this@CalenderFragment)
        }
        mTextYear.text=mCalendarView.curYear.toString()
        mYear=mCalendarView.curYear
        mTextMonthDay.text="${mCalendarView.curMonth}月${mCalendarView.curDay}日"
        mTextLunar.text="今日"
        mTextCurrentDay.text=mCalendarView.curDay.toString()

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

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CalenderFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                CalenderFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
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
        mTextLunar.visibility=View.VISIBLE
        mTextYear.visibility=View.VISIBLE
        mTextMonthDay.text="${calendar.month}月${calendar.day}日"
        mTextYear.text=calendar.year.toString()
        mTextLunar.text=calendar.lunar.toString()
        mYear=calendar.year

        if (calendar.isCurrentDay){
            Toast.makeText(requireContext(),"今天",Toast.LENGTH_LONG).show()
        }else if (calendar.day>mCalendarView.curDay){
            Toast.makeText(requireContext(),"超过范围",Toast.LENGTH_LONG).show()
        }
        if (!calendar.hasScheme()){
            calendar.apply {
                schemeColor=Color.WHITE
                scheme="签"
                addScheme(Calendar.Scheme())
                //addScheme(0xFF008800, "假")
            }
        }

    }

    override fun onYearChange(year: Int) {
        mTextMonthDay.text=year.toString()
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
        _binding=null
    }
}