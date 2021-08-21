package com.platinumstandard.eonwumah.pokedex.models

data class PokemonDetailResponse(
    val height: Int,
    val name: String,
    val id: Int,
    val stats: List<StatsData>,
    val types: List<Types>,
    val weight: Int
)

data class StatsData(
    val base_stat: Int,
    val effort: Int,
    val stat: Stat
)

data class Stat(
    val name: String,
    val url: String
)

data class Types(
    val slot: Int,
    val type: Type
)

data class Type(
    val name: String,
    val url: String
)

