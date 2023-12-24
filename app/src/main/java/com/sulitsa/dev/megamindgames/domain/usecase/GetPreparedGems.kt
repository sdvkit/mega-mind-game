package com.sulitsa.dev.megamindgames.domain.usecase

import com.sulitsa.dev.megamindgames.domain.model.Gem
import com.sulitsa.dev.megamindgames.domain.repository.GemsRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetPreparedGems @Inject constructor(
    private val gemsRepository: GemsRepository
) {

    suspend operator fun invoke(): List<Gem> {
        return gemsRepository.shuffleAndDuplicateGems()
    }
}