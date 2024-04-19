package com.example.fitnesstrakcer

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class OptionList(
    val idItem: Int,
    @StringRes val textItem: Int,
    @DrawableRes val imageItem: Int
)

