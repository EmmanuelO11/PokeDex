package com.platinumstandard.eonwumah.pokedex.ui

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.graphics.drawable.toDrawable
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.platinumstandard.eonwumah.pokedex.R
import com.platinumstandard.eonwumah.pokedex.models.PokemonDetailEntity
import com.platinumstandard.eonwumah.pokedex.utils.typeMap
import com.platinumstandard.eonwumah.pokedex.viewmodels.DetailViewModel
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.pokemon_stat_bar.view.*
import java.util.*

const val TAG = "DetailActivity"

class PokemonDetailActivity : AppCompatActivity() {

    private lateinit var viewModel: DetailViewModel
    private var pokemonName: String? = null
    private var pokemonImage: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        detail_background.background
        viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
        initObservers()
    }

    private fun initObservers() {
        getIntentFromMainActivity()

        viewModel.pokemoncharacter.observe(this, {
            initViews(it)
        })
    }

    private fun getIntentFromMainActivity() {
        val mainIntent = intent
        pokemonName = mainIntent.getStringExtra(POKEMON_NAME)
        pokemonImage = mainIntent.getStringExtra(POKEMON_IMAGE)

        pokemonName?.let { viewModel.getPokemonByName(it.toLowerCase()) }
    }

    @SuppressLint("SetTextI18n")
    private fun initViews(entity: PokemonDetailEntity) {
        pokemon_image.load(pokemonImage)
        pokemon_id_text.text = "#${entity.id} ${entity.name.capitalize(Locale.ROOT)}"

        val gradient = GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP, intArrayOf(ContextCompat.getColor(this, typeMap[entity.types[0].type.name]!!), R.color.black ))
        gradient.setGradientCenter(0.3f,0.9f)
        detail_background.background = gradient


        if (entity.types.size == 1) pokemon_type2_text.visibility = View.GONE
        for (i in entity.types.indices) {

            if (i == 0) {
                pokemon_type1_text.text = entity.types[i].type.name.capitalize(Locale.ROOT)
                DrawableCompat.setTint(
                    pokemon_type1_text.background,
                    ContextCompat.getColor(this, typeMap[entity.types[i].type.name]!!)
                )
            }

            if (i == 1) {
                pokemon_type2_text.text = entity.types[i].type.name.capitalize(Locale.ROOT)
                DrawableCompat.setTint(
                    pokemon_type2_text.background,
                    ContextCompat.getColor(this, typeMap[entity.types[i].type.name]!!)
                )
            }
        }

        weight_text.text = "${viewModel.calculateWeightAndHeight(entity.weight)}kg"
        height_text.text = "${viewModel.calculateWeightAndHeight(entity.height)}m"

        showStatProgress()
    }

    @SuppressLint("SetTextI18n")
    private fun showStatProgress() {
        hp_stat_bar.stat_type.text = "HP: ${viewModel.setPokemonStatEntity()!!.hp.toInt()}"
        hp_stat_bar.stat_progress_bar.setIndicatorColor(ContextCompat.getColor(this, R.color.yellow))
        ObjectAnimator.ofInt(hp_stat_bar.stat_progress_bar, "progress", viewModel.convertPokemonStatToPercentage()!!.hp.toInt())
            .setDuration(2000)
            .start()

        atk_stat_bar.stat_type.text = "ATK: ${viewModel.setPokemonStatEntity()!!.atk.toInt()}"
        atk_stat_bar.stat_progress_bar.setIndicatorColor(ContextCompat.getColor(this, R.color.red))
        ObjectAnimator.ofInt(atk_stat_bar.stat_progress_bar, "progress", viewModel.convertPokemonStatToPercentage()!!.atk.toInt())
            .setDuration(2000)
            .start()

        def_stat_bar.stat_type.text = "DEF: ${viewModel.setPokemonStatEntity()!!.def.toInt()}"
        def_stat_bar.stat_progress_bar.setIndicatorColor(ContextCompat.getColor(this, R.color.blue))
        ObjectAnimator.ofInt(def_stat_bar.stat_progress_bar, "progress", viewModel.convertPokemonStatToPercentage()!!.def.toInt())
            .setDuration(2000)
            .start()

        spatk_stat_bar.stat_type.text = "SpAtk: ${viewModel.setPokemonStatEntity()!!.spAtk.toInt()}"
        spatk_stat_bar.stat_progress_bar.setIndicatorColor(ContextCompat.getColor(this, R.color.purple_500))
        ObjectAnimator.ofInt(spatk_stat_bar.stat_progress_bar, "progress", viewModel.convertPokemonStatToPercentage()!!.spAtk.toInt())
            .setDuration(2000)
            .start()

        spdef_stat_bar.stat_type.text = "SpDef: ${viewModel.setPokemonStatEntity()!!.spDef.toInt()}"
        spdef_stat_bar.stat_progress_bar.setIndicatorColor(ContextCompat.getColor(this, R.color.purple_200))
        ObjectAnimator.ofInt(spdef_stat_bar.stat_progress_bar, "progress", viewModel.convertPokemonStatToPercentage()!!.spDef.toInt())
            .setDuration(2000)
            .start()

        speed_stat_bar.stat_type.text = "Speed: ${viewModel.setPokemonStatEntity()!!.speed.toInt()}"
        speed_stat_bar.stat_progress_bar.setIndicatorColor(ContextCompat.getColor(this, R.color.green))
        ObjectAnimator.ofInt(speed_stat_bar.stat_progress_bar, "progress", viewModel.convertPokemonStatToPercentage()!!.speed.toInt())
            .setDuration(2000)
            .start()
    }
}
