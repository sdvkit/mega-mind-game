package com.sulitsa.dev.megamindgames.di.modules

import android.content.Context
import com.sulitsa.dev.megamindgames.data.manager.LocalCoinManagerImpl
import com.sulitsa.dev.megamindgames.domain.manager.LocalCoinManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ManagerModule {

    @Singleton
    @Provides
    fun provideLocalCoinManager(
        context: Context
    ): LocalCoinManager = LocalCoinManagerImpl(context = context)
}