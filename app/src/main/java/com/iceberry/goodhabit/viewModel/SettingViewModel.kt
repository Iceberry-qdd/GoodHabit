package com.iceberry.goodhabit.viewModel

import android.app.Application
import android.content.SharedPreferences
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.preference.PreferenceManager

/**
 * author: Iceberry
 * email:qddwork@outlook.com
 * date: 2021/4/19
 * desc:
 *
 */
class SettingViewModel(application: Application) : AndroidViewModel(application) {
    private val context = getApplication<Application>().applicationContext
    private val sharedPreferences: SharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(context)
    val weekStartDay = MutableLiveData<String>()

    val theme = MutableLiveData<String>()

    init {
        theme.value = sharedPreferences.getString("theme", "default")
        weekStartDay.value = sharedPreferences.getString("week_start_day", "SUN")
    }
}