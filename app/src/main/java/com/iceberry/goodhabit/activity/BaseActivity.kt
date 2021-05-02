package com.iceberry.goodhabit.activity

import android.app.Activity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager
import com.iceberry.goodhabit.viewModel.SettingViewModel

/**
 * author: Iceberry
 * email:qddwork@outlook.com
 * date: 2021/4/3
 * desc:
 *
 */
open class BaseActivity : AppCompatActivity() {
    private val viewModel: SettingViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        val sharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(this /* Activity context */)
        val theme = sharedPreferences.getString("theme", "default")

        when (theme) {

            "day" -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
            "night" -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
            "default" -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
            }
            else -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
            }
        }

        super.onCreate(savedInstanceState)
        //setStatusBarFontColor(this, true)
        //window.statusBarColor = Color.rgb(255, 132, 0)
        //supportActionBar?.elevation = 0f
        //supportActionBar?.hide()
    }


    companion object {
        fun setStatusBarFontColor(activity: Activity, dark: Boolean) {
            val decorView = activity.window.decorView
            if (dark) {
                decorView.systemUiVisibility =
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            } else {
                decorView.systemUiVisibility =
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            }

        }
    }
}