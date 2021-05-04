package com.iceberry.goodhabit.fragment

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.viewModels
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import com.iceberry.goodhabit.R
import com.iceberry.goodhabit.viewModel.FragmentViewModel

class SettingsFragment : PreferenceFragmentCompat() {
    private val listener: SharedPreferences.OnSharedPreferenceChangeListener =
        SharedPreferences.OnSharedPreferenceChangeListener { sp, key ->
            when (key) {
                "theme" -> {
                    changeAppTheme(sp.getString("theme", "default"))
                }
                "language" -> {
                    changeAppLanguage(sp.getString("language", "default"))
                }
                "week_start_day" -> {
                    changeWeekStartDay(sp.getString("week_start_day", "SUN"))
                }
                "allow_reissue" -> {
                    isAllowReissue(sp.getBoolean("allow_reissue",false))
                }
                "auto_backup" -> {
                    isAutoBackup()
                }
            }
        }

    private val viewModel: FragmentViewModel by viewModels()

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings_preferences, rootKey)

        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.requireContext())
        val autoBackup = sharedPreferences.getBoolean("auto_backup", false)
        val theme = sharedPreferences.getString("theme", "default")
        val language = sharedPreferences.getString("language", "default")
        val importBackupPreference = findPreference<Preference>("import_backup")

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

    /**
     * Called when a preference in the tree requests to display a dialog. Subclasses should
     * override this method to display custom dialogs or to handle dialogs for custom preference
     * classes.
     *
     * @param preference The [Preference] object requesting the dialog
     */
    override fun onDisplayPreferenceDialog(preference: Preference?) {
        super.onDisplayPreferenceDialog(preference)

    }

    private fun isAutoBackup() {
        TODO("Not yet implemented")
    }

    private fun isAllowReissue(allowReissue:Boolean) {
        viewModel.allowReissue.value=allowReissue
    }

    private fun changeWeekStartDay(dayOfWeek: String?) {
        viewModel.weekStartDay.value = dayOfWeek
    }

    private fun changeAppLanguage(string: String?) {
        TODO("Not yet implemented")
    }

    private fun changeAppTheme(theme: String?) {
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
        }
    }

    override fun onResume() {
        super.onResume()
        preferenceManager.sharedPreferences.registerOnSharedPreferenceChangeListener(listener)
    }

    override fun onPause() {
        super.onPause()
        preferenceManager.sharedPreferences.unregisterOnSharedPreferenceChangeListener(listener)
    }
}