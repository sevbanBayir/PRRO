package com.example.prro.util

import android.content.Context
import android.content.SharedPreferences
import com.example.prro.model.CoinListModel

class OzelSharedPreferences {

    companion object {

        private var sharedPreferences: SharedPreferences? = null

        @Volatile
        private var instance: OzelSharedPreferences? = null
        private val lock = Any()
        operator fun invoke(context: Context): OzelSharedPreferences =
            instance ?: synchronized(lock) {
                instance ?: OzelSharedPreferencesYap(context).also {
                    instance = it
                }
            }

        private fun OzelSharedPreferencesYap(context: Context): OzelSharedPreferences {

            sharedPreferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(context)
            return OzelSharedPreferences()
        }
    }

    fun clickiKaydet(click: String) {

        sharedPreferences?.edit()?.putString("clickedPair", click)?.apply()
    }

    fun clickiAl() = sharedPreferences?.getString("clickedPair", "")
}