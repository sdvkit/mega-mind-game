package com.sulitsa.dev.megamindgames.domain.usecase

import com.sulitsa.dev.megamindgames.util.Constants
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CalculateCoinsBySeconds @Inject constructor() {

    operator fun invoke(seconds: Int): Int {
        if (seconds < Constants.BEST_TIME) {
            return Constants.MAX_COINS
        }

        val overtimeSeconds = seconds - Constants.BEST_TIME
        val result = Constants.MAX_COINS - overtimeSeconds * Constants.OVERTIME_COIN_FINE

        return when (result > 0) {
            true -> result
            else -> Constants.MIN_COINS
        }
    }
}