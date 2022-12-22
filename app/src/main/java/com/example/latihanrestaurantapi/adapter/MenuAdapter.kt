package com.example.latihanrestaurantapi.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.latihanrestaurantapi.databinding.ListMenuBinding
import com.example.latihanrestaurantapi.retrofit.response.FoodsItem

class MenuAdapter(private val listFood: List<FoodsItem>) :
    RecyclerView.Adapter<MenuAdapter.ViewHolder>() {
    class ViewHolder(val binding: ListMenuBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListMenuBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.tvFood.text = listFood[position].name
    }

    override fun getItemCount() = listFood.size
}