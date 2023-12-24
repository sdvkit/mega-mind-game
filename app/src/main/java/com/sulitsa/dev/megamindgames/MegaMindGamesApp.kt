package com.sulitsa.dev.megamindgames

import android.app.Application
import com.sulitsa.dev.megamindgames.di.ApplicationComponent
import com.sulitsa.dev.megamindgames.di.DaggerApplicationComponent
import com.sulitsa.dev.megamindgames.di.modules.ContextModule

class MegaMindGamesApp : Application() {
    val appComponent: ApplicationComponent = DaggerApplicationComponent.builder()
        .contextModule(ContextModule(this))
        .build()
}