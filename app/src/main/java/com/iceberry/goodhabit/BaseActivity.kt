package com.iceberry.goodhabit

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

/**
 * author: Iceberry
 * email:qddwork@outlook.com
 * date: 2021/4/3
 * desc:
 *
 */
open class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setStatusBarFontColor(this, true)
        window.statusBarColor = Color.WHITE
        supportActionBar?.elevation = 0f
    }

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