package com.sulitsa.dev.megamindgames.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.sulitsa.dev.megamindgames.MegaMindGamesApp
import com.sulitsa.dev.megamindgames.presentation.endgame.EndGamePopupScreen
import com.sulitsa.dev.megamindgames.presentation.game.GameSceneScreen
import com.sulitsa.dev.megamindgames.presentation.menu.MenuViewScreen

fun Fragment.navigateTo(resId: Int, args: Bundle? = null) {
    findNavController().navigate(resId, args)
}

fun Fragment.currentNavDestination(): String {
    return findNavController().currentDestination!!.label.toString()
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

        is EndGamePopupScreen -> {
            appComponent.inject(this)
        }
    }
}