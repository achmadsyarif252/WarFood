package com.example.latihanrestaurantapi.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.latihanrestaurantapi.DetailRestaurant
import com.example.latihanrestaurantapi.HomeReviewFragment
import com.example.latihanrestaurantapi.databinding.ItemRestaurantBinding
import com.example.latihanrestaurantapi.retrofit.response.RestaurantsItem

class RestaurantAdapter(private val listRestaurant: List<RestaurantsItem>) :
    RecyclerView.Adapter<RestaurantAdapter.ViewHolder>() {
    class ViewHolder(val binding: ItemRestaurantBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemRestaurantBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.tvNamaResto.text = listRestaurant[position].name
        holder.binding.tvRating.text = listRestaurant[position].rating.toString()
        holder.binding.tvLocation.text = listRestaurant[position].city
        Glide.with(holder.itemView.context)
            .load("https://restaurant-api.dicoding.dev/images/large/${listRestaurant[position].pictureId}")
            .transform(RoundedCorners(32))
            .into(holder.binding.ivResto)

        holder.itemView.setOnClickListener {
            HomeReviewFragment.idRestaurant = listRestaurant[position].id
            it.context.startActivity(Intent(it.context, DetailRestaurant::class.java))
        }

    }

    override fun getItemCount() = listRestaurant.size

}