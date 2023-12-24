package com.sulitsa.dev.megamindgames.domain.repository

import com.sulitsa.dev.megamindgames.domain.model.Gem

interface GemsRepository {
    suspend fun shuffleAndDuplicateGems(): List<Gem>
}