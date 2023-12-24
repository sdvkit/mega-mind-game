package com.sulitsa.dev.megamindgames.presentation.game

import com.sulitsa.dev.megamindgames.presentation.common.GameCellItem

sealed class GameSceneEvent {

    data object GetGems : GameSceneEvent()
    data object StartTimer : GameSceneEvent()
    class TriggerGameCell(val gameCell: GameCellItem) : GameSceneEvent()
    class ChangeUIAvailability(val isAvailable: Boolean) : GameSceneEvent()
    class SaveGameCellItems(val gameCellItems: List<GameCellItem>) : GameSceneEvent()
}