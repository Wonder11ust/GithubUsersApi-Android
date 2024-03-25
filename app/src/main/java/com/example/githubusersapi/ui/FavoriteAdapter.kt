package com.example.githubusersapi.ui


import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubusersapi.database.Favorite
import com.example.githubusersapi.databinding.UserItemBinding
import com.example.githubusersapi.ui.helper.FavoriteDiffCallback

class FavoriteAdapter():RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    private val favorites =ArrayList<Favorite>()

    private var onItemClickCallback:((Favorite)->Unit)?=null

    fun setOnItemClickListener(listener:(Favorite)->Unit){
        onItemClickCallback = listener
    }

    fun setListFavorites(favorites:List<Favorite>){
        this.favorites.clear()
        this.favorites.addAll(favorites)
        notifyDataSetChanged()

    }
    inner class FavoriteViewHolder(private val binding: UserItemBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(favorite: Favorite) {
            binding.tvItemName.text = favorite.username
            Glide.with(binding.root.context)
                .load(favorite.avatarUrl)
                .into(binding.profileImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding = UserItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return favorites.size
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val favorite = favorites[position]
        holder.bind(favorite)
        holder.itemView.setOnClickListener {

            onItemClickCallback?.invoke(favorite)
        }
    }



}

