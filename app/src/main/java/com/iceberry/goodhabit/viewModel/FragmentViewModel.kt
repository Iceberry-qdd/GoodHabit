package com.iceberry.goodhabit.viewModel

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.preference.PreferenceManager
import com.haibin.calendarview.Calendar
import com.iceberry.goodhabit.SchemeData
import java.io.IOException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.text.SimpleDateFormat

class FragmentViewModel(application: Application) : AndroidViewModel(application) {
    private val mSchemeDates: MutableMap<String, Calendar> by lazy { mutableMapOf() }
    private val context = getApplication<Application>().applicationContext

    private val sharedPreferences: SharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(context)
    val weekStartDay = MutableLiveData<String>()

    val theme = MutableLiveData<String>()

    //    val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
//    val weekStartDay = MutableLiveData<String>()
    val curSignIn = MutableLiveData<Boolean>()
    val allowReissue=MutableLiveData<Boolean>()
//    val theme=MutableLiveData<String>()

    init {
//        val calendar = java.util.Calendar.getInstance()
//        val formatter= SimpleDateFormat("yyyyMMdd")

        theme.value = sharedPreferences.getString("theme", "default")
        weekStartDay.value = sharedPreferences.getString("week_start_day", "SUN")
        loadSchemeDates()
        curSignIn.value = mSchemeDates.containsKey(getCurrentDay())
        allowReissue.value=sharedPreferences.getBoolean("allow_reissue",false)
//        theme.value=sharedPreferences.getString("theme","default")
//        weekStartDay.value = sharedPreferences.getString("week_start_day", "SUN")
    }

    fun getCurrentDay():String{
        val calendar = java.util.Calendar.getInstance()
        val formatter= SimpleDateFormat("yyyyMMdd")
        return formatter.format(calendar.time)
    }

    private fun loadSchemeDates() {
        try {
            val openFileInput = context.openFileInput("data.bin")
            val schemeData = ObjectInputStream(openFileInput).readObject() as SchemeData
            mSchemeDates.putAll(schemeData.mSchemeDates)
            Log.d("TAG", "read!")
        } catch (e: IOException) {
            print(e)
        }
    }

    fun saveSchemeDates() {
        val schemeData = SchemeData(1, System.currentTimeMillis(), mSchemeDates)
        try {
            val openFileOutput = context.openFileOutput("data.bin", Context.MODE_PRIVATE)
            ObjectOutputStream(openFileOutput).apply {
                writeObject(schemeData)
                flush()
                close()
                Log.d("TAG", "saved!")
            }
        } catch (e: IOException) {
            print(e)
        }
    }

    fun addSchemeDate(calendar: Calendar) {
        mSchemeDates[getSchemeCalendar(
            calendar.year,
            calendar.month,
            calendar.day,
            "戒"
        ).toString()] = getSchemeCalendar(calendar.year, calendar.month, calendar.day, "戒")
    }

    fun getSchemeDates(): MutableMap<String, Calendar> {
        return mSchemeDates
    }

    /**
     * This method will be called when this ViewModel is no longer used and will be destroyed.
     *
     *
     * It is useful when ViewModel observes some data and you need to clear this subscription to
     * prevent a leak of this ViewModel.
     */
    override fun onCleared() {
        super.onCleared()
        //saveSchemeDates()
    }

    private fun getSchemeCalendar(year: Int, month: Int, day: Int, text: String): Calendar {
        return Calendar().apply {
            this.year = year
            this.month = month
            this.day = day
            scheme = text
            schemeColor = Color.WHITE
        }
    }
}