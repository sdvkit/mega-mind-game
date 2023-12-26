package com.sulitsa.dev.megamindgames.presentation.endgame

sealed class EndGamePopupEvent {

    class CalculateCoins(val seconds: Int) : EndGamePopupEvent()

    data object UpgradeCoinsValue : EndGamePopupEvent()
}