package com.sulitsa.dev.megamindgames.domain.manager

interface LocalCoinManager {
    suspend fun upgradeValueBy(value: Int)
    suspend fun getCoinsCount(): Int
}