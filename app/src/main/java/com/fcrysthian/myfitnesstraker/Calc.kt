package com.fcrysthian.myfitnesstraker

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Calc(
    val id: Int,
    @DrawableRes val drawableId: Int,
    @StringRes val textStringId: Int,
    val color: Int
)
