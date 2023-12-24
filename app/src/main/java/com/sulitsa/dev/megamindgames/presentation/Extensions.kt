package com.sulitsa.dev.megamindgames.presentation

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.sulitsa.dev.megamindgames.MegaMindGamesApp
import com.sulitsa.dev.megamindgames.presentation.game.GameSceneScreen
import com.sulitsa.dev.megamindgames.presentation.menu.MenuViewScreen

fun Fragment.navigateTo(resId: Int) {
    findNavController().navigate(resId)
}

fun Fragment.injectDependencies() {
    val appComponent = (requireActivity().applicationContext as MegaMindGamesApp).appComponent

    when (this) {
        is MenuViewScreen -> {
            appComponent.inject(this)
        }

        is GameSceneScreen -> {
            appComponent.inject(this)
        }
    }
}