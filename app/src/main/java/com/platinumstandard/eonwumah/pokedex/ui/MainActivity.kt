package com.platinumstandard.eonwumah.pokedex.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.platinumstandard.eonwumah.pokedex.R
import com.platinumstandard.eonwumah.pokedex.paging.PokemonListAdapter
import com.platinumstandard.eonwumah.pokedex.viewmodels.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_grid_item.*
import kotlinx.coroutines.flow.collectLatest
import java.util.*

const val POKEMON_NAME = "POKEMON_NAME"
const val POKEMON_IMAGE = "POKEMON_IMAGE"
class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var pokemonAdapter: PokemonListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initObservers()
        initAdapter()
    }

    private fun initAdapter() {
        recyclerView.apply {
            pokemonAdapter = PokemonListAdapter(listener =  {
                val startDetailActivityIntent = Intent(this@MainActivity, PokemonDetailActivity::class.java)
                startDetailActivityIntent.putExtra(POKEMON_NAME, it.name)
                startDetailActivityIntent.putExtra(POKEMON_IMAGE, it.image)
                startActivity(startDetailActivityIntent)
            })
            adapter = pokemonAdapter
            layoutManager = GridLayoutManager(this@MainActivity, 2)
        }
    }

    private fun initObservers() {
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        lifecycleScope.launchWhenCreated {
            viewModel.getAllPokemon().collectLatest {
                pokemonAdapter.submitData(it)
            }
        }
    }
}