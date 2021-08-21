package com.platinumstandard.eonwumah.pokedex.paging

import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.imageLoader
import coil.request.ImageRequest
import com.platinumstandard.eonwumah.pokedex.R
import com.platinumstandard.eonwumah.pokedex.models.PokemonListEntity
import com.platinumstandard.eonwumah.pokedex.utils.getPokemonColor
import kotlinx.android.synthetic.main.layout_grid_item.view.*

class PokemonListAdapter(private val listener:(data: PokemonListEntity) -> Unit): PagingDataAdapter<PokemonListEntity, PokemonListAdapter.PokemonViewHolder>(PokemonListDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        return PokemonViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.layout_grid_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        holder.bind(getItem(position)!!, listener)
    }

    inner class PokemonViewHolder(val view: View): RecyclerView.ViewHolder(view) {
        val name = view.card_name
        val image = view.card_image
        val backgroundCard = view.card

        fun bind(pokemonListEntity: PokemonListEntity, clickListener: (PokemonListEntity) -> Unit) {
            name.text = pokemonListEntity.name

            //Logic for dynamic cardview color
            val imageLoader = image.context.imageLoader
            val imageRequest = ImageRequest.Builder(image.context)
                .data(pokemonListEntity.image)
                .target{
                    image.setImageDrawable(it)
                    val colorList =
                        getPokemonColor(it)?.rgb?.let { it1 -> intArrayOf(it1, ContextCompat.getColor(backgroundCard.context, R.color.white)) }
                    val gradient = GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, colorList)
                    gradient.setGradientCenter(0.5f,0.85f)
                    gradient.cornerRadius = 20f
                    backgroundCard.background = gradient
                }
                .build()
            imageLoader.enqueue(imageRequest)

            view.setOnClickListener { clickListener(pokemonListEntity) }
        }
    }

}

class PokemonListDiffCallBack : DiffUtil.ItemCallback<PokemonListEntity>() {
    override fun areItemsTheSame(oldItem: PokemonListEntity, newItem: PokemonListEntity): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: PokemonListEntity, newItem: PokemonListEntity): Boolean {
        return oldItem == newItem
    }

}

