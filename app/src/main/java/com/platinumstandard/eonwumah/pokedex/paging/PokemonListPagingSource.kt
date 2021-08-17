package com.platinumstandard.eonwumah.pokedex.paging

import android.net.Uri
import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.platinumstandard.eonwumah.pokedex.models.PokemonEntity
import com.platinumstandard.eonwumah.pokedex.network.PokemonService
import com.platinumstandard.eonwumah.pokedex.utils.FIRST_PAGE
import com.platinumstandard.eonwumah.pokedex.utils.NETWORK_LOG
import java.lang.Exception

class PokemonListPagingSource(private val service: PokemonService): PagingSource<Int, PokemonEntity>() {
    override fun getRefreshKey(state: PagingState<Int, PokemonEntity>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PokemonEntity> {
        return try {
            val nextPage: Int = params.key ?: FIRST_PAGE
            val response = service.getAllPokemon(offset = nextPage)
            val newEntity = response.results.mapIndexed { index, pokemonData ->
                PokemonEntity(name = pokemonData.name,
                    url = pokemonData.url,
                    image = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${index + 1}.png")
            }
            var nextOffset:Int? = null
            if (response.next != null) {
                val uri = Uri.parse((response.next))
                val nextQuery = uri.getQueryParameter("offset")
                nextOffset = nextQuery?.toInt()
            }
            Log.d(NETWORK_LOG, "PokemonEntity: $newEntity")
            LoadResult.Page(
                data = newEntity,
                prevKey = null,
                nextKey = nextOffset
            )


        } catch (e: Exception) {
            Log.d(NETWORK_LOG, "load: $e ")
            LoadResult.Error(e)
        }
    }
}