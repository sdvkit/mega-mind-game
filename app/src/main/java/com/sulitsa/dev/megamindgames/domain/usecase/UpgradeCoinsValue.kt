package com.sulitsa.dev.megamindgames.domain.usecase

import com.sulitsa.dev.megamindgames.domain.manager.LocalCoinManager
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UpgradeCoinsValue @Inject constructor(
    private val localCoinManager: LocalCoinManager
) {

    suspend operator fun invoke(coins: Int) {
        localCoinManager.upgradeValueBy(value = coins)
    }
}