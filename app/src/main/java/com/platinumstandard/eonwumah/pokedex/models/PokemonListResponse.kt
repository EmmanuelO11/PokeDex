package com.platinumstandard.eonwumah.pokedex.models

data class PokemonListResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<PokemonData>
)

data class PokemonData(
    val name: String,
    val url: String
)

