package com.example.latihanrestaurantapi.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.latihanrestaurantapi.databinding.ReviewItemBinding
import com.example.latihanrestaurantapi.retrofit.response.CustomerReviewsItem

class ReviewAdapter(private val listReview: List<CustomerReviewsItem>) :
    RecyclerView.Adapter<ReviewAdapter.ViewHolder>() {
    class ViewHolder(val binding: ReviewItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ReviewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val review = """
            ${listReview[position].review}
            -${listReview[position].name}
        """.trimIndent()
        holder.binding.tvReviewItem.text = review
    }

    override fun getItemCount() = listReview.size
}