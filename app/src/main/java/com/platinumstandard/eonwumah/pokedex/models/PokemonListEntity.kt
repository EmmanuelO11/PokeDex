package com.platinumstandard.eonwumah.pokedex.models

data class PokemonListEntity(
    var name: String,
    var url: String,
    var image: String = ""
)