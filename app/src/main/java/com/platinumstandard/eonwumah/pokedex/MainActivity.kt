package com.platinumstandard.eonwumah.pokedex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.platinumstandard.eonwumah.pokedex.paging.PokemonListAdapter
import com.platinumstandard.eonwumah.pokedex.viewmodels.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.flow.collectLatest

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var pokemonAdapter: PokemonListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initAdapter()
        initObservers()
    }

    private fun initAdapter() {
        recyclerView.apply {
            pokemonAdapter = PokemonListAdapter()
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