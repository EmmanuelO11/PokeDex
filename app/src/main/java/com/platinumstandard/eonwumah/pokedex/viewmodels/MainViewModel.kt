package com.platinumstandard.eonwumah.pokedex.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.platinumstandard.eonwumah.pokedex.models.PokemonEntity
import com.platinumstandard.eonwumah.pokedex.network.PokemonService
import com.platinumstandard.eonwumah.pokedex.network.RetrofitApi
import com.platinumstandard.eonwumah.pokedex.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow

class MainViewModel: ViewModel() {
    var repository: PokemonRepository

    init {
        val service: PokemonService = RetrofitApi.getRetrofitInstance().create(PokemonService::class.java)
        repository = PokemonRepository(service)
    }


    fun getAllPokemon(): Flow<PagingData<PokemonEntity>> {
        return repository.getAllPokemon().cachedIn(viewModelScope)
    }
}