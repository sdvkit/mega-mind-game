package com.sulitsa.dev.megamindgames.presentation.menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sulitsa.dev.megamindgames.domain.usecase.GetCoinsCount
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MenuViewModel @Inject constructor(
    private val getCoinsCount: GetCoinsCount
) : ViewModel() {

    private val _state = MutableStateFlow(MenuState())
    val state: StateFlow<MenuState> = _state

    init {
        onEvent(MenuEvent.GetCoinsCount)
    }

    fun onEvent(event: MenuEvent) {
        when (event) {
            is MenuEvent.GetCoinsCount -> {
                getLocalCoinsCount()
            }
        }
    }

    private fun getLocalCoinsCount() {
        viewModelScope.launch {
            val coinsCount = getCoinsCount()

            _state.update { currentState ->
                currentState.copy(currentCoinsCount = coinsCount)
            }
        }
    }
}