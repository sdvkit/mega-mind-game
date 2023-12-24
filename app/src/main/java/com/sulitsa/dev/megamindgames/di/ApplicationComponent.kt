package com.sulitsa.dev.megamindgames.di

import com.sulitsa.dev.megamindgames.di.modules.ContextModule
import com.sulitsa.dev.megamindgames.di.modules.ManagerModule
import com.sulitsa.dev.megamindgames.di.modules.RepositoryModule
import com.sulitsa.dev.megamindgames.presentation.game.GameSceneScreen
import com.sulitsa.dev.megamindgames.presentation.menu.MenuViewScreen
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    ContextModule::class,
    ManagerModule::class,
    RepositoryModule::class
])
interface ApplicationComponent {
    fun inject(menuViewScreen: MenuViewScreen)
    fun inject(gameSceneScreen: GameSceneScreen)
}