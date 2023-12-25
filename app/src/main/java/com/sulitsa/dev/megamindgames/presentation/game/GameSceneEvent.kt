package com.sulitsa.dev.megamindgames.presentation.game

import com.sulitsa.dev.megamindgames.presentation.common.GameCellItem

sealed class GameSceneEvent {

    data object GetGems : GameSceneEvent()

    data object StartTimer : GameSceneEvent()

    data object StopTimer : GameSceneEvent()

    class TriggerGameCell(
        val gameCell: GameCellItem,
        val ifGemsAreDifferentAction: (GameCellItem, GameCellItem) -> Unit
    ) : GameSceneEvent()

    class ChangeUIAvailability(val isAvailable: Boolean) : GameSceneEvent()

    class SaveGameCellItems(val gameCellItems: List<GameCellItem>) : GameSceneEvent()

    data object GetCoinsCount : GameSceneEvent()

    data object StartNewGame : GameSceneEvent()
}