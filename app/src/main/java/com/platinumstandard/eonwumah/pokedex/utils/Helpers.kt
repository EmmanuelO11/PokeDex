package com.platinumstandard.eonwumah.pokedex.utils

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.palette.graphics.Palette

const val OFFSET = 30
const val FIRST_PAGE = 0
const val NETWORK_LOG = "NETWORK_LOG"

fun getPokemonColor(drawable: Drawable): Palette.Swatch? {
    val bmp = (drawable as BitmapDrawable).bitmap.copy(Bitmap.Config.ARGB_8888, true)
    val palette = Palette.from(bmp).generate()
    return palette.dominantSwatch
}