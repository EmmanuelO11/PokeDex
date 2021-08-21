package com.platinumstandard.eonwumah.pokedex.utils

import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.palette.graphics.Palette
import com.platinumstandard.eonwumah.pokedex.R
import com.platinumstandard.eonwumah.pokedex.models.PokemonDetailEntity
import com.platinumstandard.eonwumah.pokedex.models.PokemonDetailResponse

const val OFFSET = 30
const val FIRST_PAGE = 0
const val NETWORK_LOG = "NETWORK_LOG"

fun getPokemonColor(drawable: Drawable): Palette.Swatch? {
    val bmp = (drawable as BitmapDrawable).bitmap.copy(Bitmap.Config.ARGB_8888, true)
    val palette = Palette.from(bmp).generate()
    return palette.dominantSwatch
}

fun PokemonDetailResponse.toEntity(): PokemonDetailEntity {
    return PokemonDetailEntity(
        id = this.id,
        name = this.name,
        height = this.height,
        weight = this.weight,
        statsData = this.stats,
        types = this.types
    )
}

val typeMap :HashMap<String, Int> = hashMapOf(
    "normal" to R.color.gray,
    "fire" to R.color.orange,
    "water" to R.color.water,
    "electric" to R.color.teal_200,
    "grass" to R.color.grass,
    "ice" to R.color.ice,
    "fight" to R.color.fight,
    "poison" to R.color.purple_500,
    "ground" to R.color.gray,
    "flying" to R.color.ice,
    "psychic" to R.color.blue,
    "bug" to R.color.grass,
    "rock" to R.color.gray,
    "ghost" to R.color.ice,
    "dragon" to R.color.orange,
    "dark" to R.color.black,
    "steel" to R.color.steel,
    "fairy" to R.color.teal_200,
    "unknown" to R.color.gray,
    "shadow" to R.color.shadow
)