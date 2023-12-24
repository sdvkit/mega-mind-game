package com.sulitsa.dev.megamindgames.di.modules

import com.sulitsa.dev.megamindgames.data.repository.GemsRepositoryImpl
import com.sulitsa.dev.megamindgames.domain.repository.GemsRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Singleton
    @Provides
    fun provideGemsRepository(): GemsRepository =
        GemsRepositoryImpl()
}