package com.sulitsa.dev.megamindgames.data.manager

import android.content.Context
import com.sulitsa.dev.megamindgames.domain.manager.LocalCoinManager
import com.sulitsa.dev.megamindgames.util.Constants
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalCoinManagerImpl @Inject constructor(
    private val context: Context
) : LocalCoinManager {

    override suspend fun upgradeValueBy(value: Int) {
        val sharedPref = context.getSharedPreferences(Constants.SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val currentCoinsValue = sharedPref.getInt(Constants.COINS_SHARED_PREF_KEY, 0)
        val newCoinsValue = currentCoinsValue + value

        sharedPref.edit().apply {
            putInt(Constants.COINS_SHARED_PREF_KEY, newCoinsValue)
            apply()
        }
    }

    override suspend fun getCoinsCount(): Int {
        val sharedPref = context.getSharedPreferences(Constants.SHARED_PREF_NAME, Context.MODE_PRIVATE)
        return sharedPref.getInt(Constants.COINS_SHARED_PREF_KEY, 0)
    }
}