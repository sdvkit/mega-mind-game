package com.sulitsa.dev.megamindgames.data.repository

import com.sulitsa.dev.megamindgames.R
import com.sulitsa.dev.megamindgames.domain.model.Gem
import com.sulitsa.dev.megamindgames.domain.repository.GemsRepository
import javax.inject.Singleton

@Singleton
class GemsRepositoryImpl : GemsRepository {

    private val gems: List<Gem> by lazy {
        listOf(
            Gem(id = 1, imageResId = R.drawable.ic_gem_1_512),
            Gem(id = 2, imageResId = R.drawable.ic_gem_2_512),
            Gem(id = 3, imageResId = R.drawable.ic_gem_3_512),
            Gem(id = 4, imageResId = R.drawable.ic_gem_4_512),
            Gem(id = 5, imageResId = R.drawable.ic_gem_5_512),
            Gem(id = 6, imageResId = R.drawable.ic_gem_6_512),
            Gem(id = 7, imageResId = R.drawable.ic_gem_7_512),
            Gem(id = 8, imageResId = R.drawable.ic_gem_8_512),
            Gem(id = 9, imageResId = R.drawable.ic_gem_9_512),
            Gem(id = 10, imageResId = R.drawable.ic_gem_10_512)
        )
    }

    override suspend fun shuffleAndDuplicateGems(): List<Gem> {
        val duplicatedGemsList = gems + gems
        return duplicatedGemsList.shuffled()
    }
}