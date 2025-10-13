package com.j.rickroller

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import io.github.dot166.jlib.app.jConfigActivity

class SettingsActivity : jConfigActivity() {
    override fun preferenceFragment(): PreferenceFragmentCompat {
        return RickrollerSettingsFragment()
    }

    class RickrollerSettingsFragment : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.rickroller_preferences, rootKey)

            findPreference<Preference?>("pref_about")!!.onPreferenceClickListener =
                Preference.OnPreferenceClickListener { preference ->
                    startActivity(Intent(preference.context, About::class.java))
                    true
                }
            if (activity != null && !TextUtils.isEmpty(preferenceScreen.title)) {
                requireActivity().setTitle(preferenceScreen.title)
            }
        }
    }
}
