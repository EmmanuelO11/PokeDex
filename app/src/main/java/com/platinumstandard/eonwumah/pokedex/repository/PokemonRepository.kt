package com.platinumstandard.eonwumah.pokedex.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.platinumstandard.eonwumah.pokedex.models.PokemonEntity
import com.platinumstandard.eonwumah.pokedex.network.PokemonService
import com.platinumstandard.eonwumah.pokedex.paging.PokemonListPagingSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PokemonRepository(private val apiService: PokemonService) {
    fun getAllPokemon(): Flow<PagingData<PokemonEntity>> {
        return Pager(
            config = PagingConfig(pageSize = 30, enablePlaceholders = false),
            pagingSourceFactory = {PokemonListPagingSource(apiService)}
        ).flow
    }
}