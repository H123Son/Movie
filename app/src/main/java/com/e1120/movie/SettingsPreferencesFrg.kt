package com.e1120.movie

import android.content.SharedPreferences
import android.os.Bundle
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat

class SettingsPreferencesFrg : PreferenceFragmentCompat(),
    SharedPreferences.OnSharedPreferenceChangeListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        preferenceScreen.sharedPreferences?.registerOnSharedPreferenceChangeListener(this)
    }


    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.preferences)
        val prefScreen = preferenceScreen
        val sharePref = prefScreen.sharedPreferences
        val count = prefScreen.preferenceCount
        for (i in 0 until (count)) {
            val pref = prefScreen.getPreference(i)
            if (pref is ListPreference) {
                setSumary(pref, sharePref!!.getString(pref.key, ""))
            }
        }
    }

    private fun setSumary(pref: ListPreference, value: String?) {
        val listPreference = pref as ListPreference
        val index = listPreference.findIndexOfValue(value)
        if (index >= 0) {
            listPreference.summary = listPreference.entries?.get(index)
        }
    }

    override fun onSharedPreferenceChanged(sPref: SharedPreferences?, key: String?) {
        val pref = key?.let { findPreference<Preference>(it) }
        if (pref is ListPreference) {
            setSumary(pref, sPref?.getString(key, ""))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        preferenceScreen.sharedPreferences?.unregisterOnSharedPreferenceChangeListener(this)
    }
}