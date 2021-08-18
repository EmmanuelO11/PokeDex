package com.platinumstandard.eonwumah.pokedex.paging

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.paging.PagingDataAdapter
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.imageLoader
import coil.request.ImageRequest
import com.platinumstandard.eonwumah.pokedex.R
import com.platinumstandard.eonwumah.pokedex.models.PokemonEntity
import com.platinumstandard.eonwumah.pokedex.utils.getPokemonColor
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

    inner class PokemonViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val name = view.card_name
        val image = view.card_image
        val backgroundCard = view.card

        fun bind(pokemonEntity: PokemonEntity) {
            name.text = pokemonEntity.name

            //Logic for dynamic cardview color
            val imageLoader = image.context.imageLoader
            val imageRequest = ImageRequest.Builder(image.context)
                .data(pokemonEntity.image)
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
