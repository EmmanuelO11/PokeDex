package com.platinumstandard.eonwumah.pokedex.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.platinumstandard.eonwumah.pokedex.models.PokemonDetailEntity
import com.platinumstandard.eonwumah.pokedex.models.PokemonStatEntity
import com.platinumstandard.eonwumah.pokedex.models.StatsData
import com.platinumstandard.eonwumah.pokedex.network.PokemonService
import com.platinumstandard.eonwumah.pokedex.network.RetrofitApi
import com.platinumstandard.eonwumah.pokedex.repository.PokemonRepository
import kotlinx.coroutines.launch
import kotlin.math.max


const val TAG = "DetailViewModel"
class DetailViewModel: ViewModel() {
    var repository: PokemonRepository

    init {
        val service: PokemonService = RetrofitApi.getRetrofitInstance().create(PokemonService::class.java)
        repository = PokemonRepository(service)
    }

    val _pokemonCharacter = MutableLiveData<PokemonDetailEntity>()
    val pokemoncharacter:LiveData<PokemonDetailEntity> = _pokemonCharacter

    fun getPokemonByName(name: String) {
        viewModelScope.launch {
            _pokemonCharacter.value = repository.getPokemonByName(name)
            Log.d(TAG, "${repository.getPokemonByName(name)}")
        }
    }

    fun calculateMaxStat(): Float {
        var maxStat = Float.MIN_VALUE
        val statsList: List<StatsData>? = _pokemonCharacter.value?.statsData
        statsList?.forEach {
            maxStat = max(maxStat, it.base_stat.toFloat())
        }
        return maxStat
    }

    fun setPokemonStatEntity(): PokemonStatEntity? {

        val entity = _pokemonCharacter.value?.let {
            PokemonStatEntity(
                hp = (it.statsData[0].base_stat.toFloat()),
                atk = it.statsData[1].base_stat.toFloat(),
                def = it.statsData[2].base_stat.toFloat(),
                spAtk = it.statsData[3].base_stat.toFloat(),
                spDef = it.statsData[4].base_stat.toFloat(),
                speed = it.statsData[5].base_stat.toFloat()
            )
        }

        Log.d(TAG, "PokemonStatEntity: $entity")
        return entity
    }

    fun convertPokemonStatToPercentage(): PokemonStatEntity? {
        val maxStat = calculateMaxStat()
        val result = setPokemonStatEntity()?.let {
            PokemonStatEntity(
                hp = it.hp/maxStat * 100,
                atk = it.atk/maxStat * 100,
                def = it.def/maxStat * 100,
                spAtk = it.spAtk/maxStat * 100,
                spDef = it.spDef/maxStat * 100,
                speed = it.speed/maxStat * 100
            )
        }

        Log.d(TAG, "PokemonStatPercents: $result")
        return result
    }

    fun calculateWeightAndHeight(data: Int): String {
        return (data.toFloat()/10f).toString()
    }
}