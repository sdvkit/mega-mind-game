package com.sulitsa.dev.megamindgames.domain.model

import androidx.annotation.DrawableRes

data class Gem(
    val id: Int,
    @DrawableRes val imageResId: Int,
    var isEnabled: Boolean = true
)