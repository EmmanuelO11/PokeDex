package com.platinumstandard.eonwumah.pokedex.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.platinumstandard.eonwumah.pokedex.models.PokemonDetailEntity
import com.platinumstandard.eonwumah.pokedex.models.PokemonListEntity
import com.platinumstandard.eonwumah.pokedex.network.PokemonService
import com.platinumstandard.eonwumah.pokedex.paging.PokemonListPagingSource
import com.platinumstandard.eonwumah.pokedex.utils.toEntity
import kotlinx.coroutines.flow.Flow

class PokemonRepository(private val apiService: PokemonService) {
    fun getAllPokemon(): Flow<PagingData<PokemonListEntity>> {
        return Pager(
            config = PagingConfig(pageSize = 30),
            pagingSourceFactory = {PokemonListPagingSource(apiService)}
        ).flow
    }

    suspend fun getPokemonByName(name: String): PokemonDetailEntity {
        return apiService.getPokemonByName(name).toEntity()
    }
}