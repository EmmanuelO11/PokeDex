package com.platinumstandard.eonwumah.pokedex.models

data class PokemonDetailEntity(
    val id: Int,
    val name: String,
    val image: String = "",
    val height: Int,
    val weight: Int,
    val statsData: List<StatsData>,
    val types: List<Types>
)