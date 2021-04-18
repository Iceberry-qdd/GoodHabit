package com.iceberry.goodhabit.viewModel

import android.app.Application
import android.content.Context
import android.graphics.Color
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.haibin.calendarview.Calendar
import com.iceberry.goodhabit.SchemeData
import java.io.IOException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

class CalendarFragmentViewModel(application: Application) : AndroidViewModel(application) {
    private val mSchemeDates: MutableMap<String, Calendar> by lazy { mutableMapOf() }
    private val context = getApplication<Application>().applicationContext

    init {
        loadSchemeDates()
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

    private fun saveSchemeDates() {
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
            "签"
        ).toString()] = getSchemeCalendar(calendar.year, calendar.month, calendar.day, "签")
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
        saveSchemeDates()
    }
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