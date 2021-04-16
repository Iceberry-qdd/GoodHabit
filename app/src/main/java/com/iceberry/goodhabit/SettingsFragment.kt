package com.iceberry.goodhabit

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings_preferences, rootKey)

        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.requireContext())
        val autoBackup = sharedPreferences.getBoolean("auto_backup", false)
        val theme = sharedPreferences.getString("theme", "default")
        val language = sharedPreferences.getString("language", "default")
        val importBackupPreference = findPreference<Preference>("import_backup")

        Log.d("TAG", "自动备份：$autoBackup")
        Log.d("TAG", "主题：$theme")
        Log.d("TAG", "语言：$language")

        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            type = "text/plain"
            addCategory(Intent.CATEGORY_OPENABLE)
        }

        importBackupPreference?.intent = intent

//        val aboutPreference=findPreference<Preference>("about")
//        aboutPreference?.setOnPreferenceClickListener {
//            Log.d("TAG","关于导航")
//            requireActivity().supportFragmentManager
//                .beginTransaction()
//                .replace(R.id.settingsFragment, AboutFragment::class.java, null)
//                .addToBackStack(null)
//                .commit()
//            true
//        }
    }
}