package com.iceberry.goodhabit

import android.os.Bundle
import android.view.MenuItem

class AboutActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

//        val color=Color.parseColor("#ffffff")
//        val drawable=ColorDrawable(color)
        supportActionBar?.apply {
            title="关于"
            setHomeButtonEnabled(true)
            setDisplayHomeAsUpEnabled(true)
        }

//        val titleId=Resources.getSystem().getIdentifier("action_bar_title","id","android")
//        val title=findViewById<TextView>(titleId)
//        title.setTextColor(Color.parseColor("#000000"))
        //actionBar?.setBackgroundDrawable(drawable)

        //window.statusBarColor= Color.WHITE
        //setStatusBarFontColor(this, true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return true
        //return super.onOptionsItemSelected(item)
    }
}