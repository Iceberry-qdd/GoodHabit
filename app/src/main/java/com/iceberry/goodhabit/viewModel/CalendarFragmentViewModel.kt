package com.iceberry.goodhabit.viewModel

import android.app.Application
import android.content.Context
import android.graphics.Color
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.haibin.calendarview.Calendar
import com.iceberry.goodhabit.SchemeData
import java.io.FileInputStream
import java.io.IOException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

class CalendarFragmentViewModel(application: Application) : AndroidViewModel(application) {
    private val mSchemeDates: MutableMap<String, Calendar> by lazy { mutableMapOf() }

    init {

        loadSchemeDates()
    }

    private fun loadSchemeDates() {
        try {
            mSchemeDates.putAll(ObjectInputStream(FileInputStream("data.bin")).readObject() as MutableMap<String, Calendar>)

        } catch (e: IOException) {
            print(e)
        }
    }

    fun saveSchemeDates() {
        val schemeData = SchemeData(1, System.currentTimeMillis(), mSchemeDates)
        try {
            val openFileOutput = getApplication<Application>().applicationContext.openFileOutput(
                "data.bin",
                Context.MODE_PRIVATE
            )
            ObjectOutputStream(openFileOutput).apply {
                writeObject(schemeData)
                flush()
                close()
                Log.d("TAG", "saved!")
            }
//            ObjectOutputStream(FileOutputStream("data.bin", true)).apply {
//                writeObject(mSchemeDates)
//                flush()
//                close()
//                Log.d("TAG", "saved!")
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