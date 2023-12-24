package com.sulitsa.dev.megamindgames.presentation.game

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sulitsa.dev.megamindgames.domain.usecase.GetPreparedGems
import com.sulitsa.dev.megamindgames.presentation.common.GameCellItem
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GameSceneViewModel @Inject constructor(
    private val getPreparedGems: GetPreparedGems
) : ViewModel() {

    private val _state = MutableStateFlow(GameSceneState())
    val state: StateFlow<GameSceneState> = _state

    private val mainHandler = Handler(Looper.getMainLooper())
    private lateinit var timerRunnable: Runnable

    init {
        onEvent(GameSceneEvent.GetGems)
        onEvent(GameSceneEvent.StartTimer)
    }

    fun onEvent(event: GameSceneEvent) {
        when (event) {
            is GameSceneEvent.GetGems -> {
                getGems()
            }

            is GameSceneEvent.StartTimer -> {
                startTimer()
            }

            is GameSceneEvent.TriggerGameCell -> {
                triggerAndCompareGem(gameCell = event.gameCell)
            }

            is GameSceneEvent.ChangeUIAvailability -> {
                changeUiAvailability(isAvailable = event.isAvailable)
            }

            is GameSceneEvent.SaveGameCellItems -> {
                saveGameCellItems(gameCellItems = event.gameCellItems)
            }
        }
    }

    private fun saveGameCellItems(gameCellItems: List<GameCellItem>) {
        _state.update { currentState ->
            currentState.copy(gameCellItems = gameCellItems)
        }
    }

    private fun changeUiAvailability(isAvailable: Boolean) {
        _state.update { currentState ->
            currentState.copy(isUiAvailable = isAvailable)
        }
    }

    private fun triggerAndCompareGem(gameCell: GameCellItem) {
        viewModelScope.launch {
            if (_state.value.lastTriggeredGameCell == null) {
                _state.update { currentState ->
                    currentState.copy(lastTriggeredGameCell = gameCell)
                }

                _state.value.lastTriggeredGameCell!!.showGem()
                _state.value.lastTriggeredGameCell!!.isCellEnabled = false

                return@launch
            }

            onEvent(GameSceneEvent.ChangeUIAvailability(isAvailable = false))

            gameCell.showGem()

            when(gameCell) {
                _state.value.lastTriggeredGameCell -> {
                    gameCell.setGemEnabled(isEnabled = false)
                    _state.value.lastTriggeredGameCell!!.setGemEnabled(isEnabled = false)

                    _state.update { currentState ->
                        currentState.copy(lastTriggeredGameCell = null)
                    }
                }

                else -> {
                    delay(1_500)
                    gameCell.hideGem()
                    _state.value.lastTriggeredGameCell!!.hideGem()
                    _state.value.lastTriggeredGameCell!!.isCellEnabled = true

                    _state.update { currentState ->
                        currentState.copy(lastTriggeredGameCell = null)
                    }
                }
            }

            onEvent(GameSceneEvent.ChangeUIAvailability(isAvailable = true))
        }
    }

    private fun getGems() {
        viewModelScope.launch {
            val gems = getPreparedGems()

            _state.update { currentState ->
                currentState.copy(gems = gems)
            }
        }
    }

    private fun startTimer() {
        var timerCounter = 0

        timerRunnable = object : Runnable {
            override fun run() {
                timerCounter++

                _state.update { currentState ->
                    currentState.copy(timerValue = timerCounter)
                }

                mainHandler.postDelayed(this, 1000)
            }
        }

        mainHandler.postDelayed(timerRunnable, 1000)
    }
}