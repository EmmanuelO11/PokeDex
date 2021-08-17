package com.platinumstandard.eonwumah.pokedex.paging

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.platinumstandard.eonwumah.pokedex.R
import com.platinumstandard.eonwumah.pokedex.models.PokemonEntity
import kotlinx.android.synthetic.main.layout_grid_item.view.*

class PokemonListAdapter: PagingDataAdapter<PokemonEntity, PokemonListAdapter.PokemonViewHolder>(PokemonListDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        return PokemonViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.layout_grid_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        holder.bind(getItem(position)!!)
    }

    inner class PokemonViewHolder(private val view: View): RecyclerView.ViewHolder(view) {
        val name = view.card_name
        val image = view.card_image

        fun bind(pokemonEntity: PokemonEntity) {
            name.text = pokemonEntity.name
            image.load(pokemonEntity.image)
        }
    }


}

class PokemonListDiffCallBack : DiffUtil.ItemCallback<PokemonEntity>() {
    override fun areItemsTheSame(oldItem: PokemonEntity, newItem: PokemonEntity): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: PokemonEntity, newItem: PokemonEntity): Boolean {
        return oldItem == newItem
    }

}
