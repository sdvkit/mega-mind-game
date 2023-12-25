package com.sulitsa.dev.megamindgames.presentation.game

import com.sulitsa.dev.megamindgames.domain.model.Gem
import com.sulitsa.dev.megamindgames.presentation.common.GameCellItem

data class GameSceneState(
    val gems: List<Gem> = emptyList(),
    val timerValue: Int = 0,
    val isUiAvailable: Boolean = true,
    val gameCellItems: List<GameCellItem> = emptyList(),
    val lastTriggeredGameCell: GameCellItem? = null,
    val isGameFinished: Boolean = false
)