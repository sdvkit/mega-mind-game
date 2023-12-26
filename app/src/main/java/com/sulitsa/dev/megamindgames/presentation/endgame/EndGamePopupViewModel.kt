package com.sulitsa.dev.megamindgames.presentation.endgame

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sulitsa.dev.megamindgames.domain.usecase.CalculateCoinsBySeconds
import com.sulitsa.dev.megamindgames.domain.usecase.UpgradeCoinsValue
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EndGamePopupViewModel @Inject constructor(
    private val calculateCoinsBySeconds: CalculateCoinsBySeconds,
    private val upgradeCoinsValue: UpgradeCoinsValue
) : ViewModel() {

    private val _state = MutableStateFlow(EndGamePopupState())
    val state: StateFlow<EndGamePopupState> = _state

    init {
        _state.update { _ ->
            EndGamePopupState()
        }
    }

    fun onEvent(event: EndGamePopupEvent) {
        when (event) {
            is EndGamePopupEvent.CalculateCoins -> {
                calculateCoins(seconds = event.seconds)
            }

            is EndGamePopupEvent.UpgradeCoinsValue -> {
                upgradeLocalCoinsValue()
            }
        }
    }

    private fun calculateCoins(seconds: Int) {
        val coinsCount = calculateCoinsBySeconds(seconds = seconds)

        viewModelScope.launch {
            _state.update { currentState ->
                currentState.copy(coinsCount = coinsCount)
            }
        }
    }

    private fun upgradeLocalCoinsValue() {
        viewModelScope.launch {
            upgradeCoinsValue(coins = _state.value.coinsCount)
        }
    }
}