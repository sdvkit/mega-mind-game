package com.sulitsa.dev.megamindgames.presentation.menu

sealed class MenuEvent {

    data object GetCoinsCount : MenuEvent()
}