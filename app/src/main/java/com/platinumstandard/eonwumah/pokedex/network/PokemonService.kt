package com.platinumstandard.eonwumah.pokedex.network

import com.platinumstandard.eonwumah.pokedex.models.PokemonListResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface PokemonService {
    @GET("pokemon")
    suspend fun getAllPokemon(@Query("offset") offset: Int,
                      @Query("limit") limit: Int = 30): PokemonListResponse
}
