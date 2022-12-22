package com.example.latihanrestaurantapi

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.latihanrestaurantapi.adapter.ReviewAdapter
import com.example.latihanrestaurantapi.databinding.FragmentHomeReviewBinding
import com.example.latihanrestaurantapi.retrofit.api.ApiConfig
import com.example.latihanrestaurantapi.retrofit.response.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeReviewFragment : Fragment() {
    private lateinit var binding: FragmentHomeReviewBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeReviewBinding.inflate(inflater, container, false)

        getRestaurantDetail()
        binding.btnSend.setOnClickListener {
            if (binding.edtReview.text.toString().isNotEmpty()) {
                val client = ApiConfig.getApiService()
                    .postReview(idRestaurant, "Achmad Syarif", binding.edtReview.text.toString())

                client.enqueue(object : Callback<PostReviewResponse> {
                    override fun onFailure(call: Call<PostReviewResponse>, t: Throwable) {
                        Log.e(TAG, "onFailure: ${t.message.toString()}")
                    }

                    override fun onResponse(
                        call: Call<PostReviewResponse>,
                        response: Response<PostReviewResponse>
                    ) {

                        if (response.isSuccessful) {
                            val responseBody = response.body()
                            if (responseBody != null) {
                                populateReview(responseBody.customerReviews as List<CustomerReviewsItem>)
                            }
                        }
                    }
                })
            } else {
                binding.edtReview.error = "Field ini masih kosong"
            }
        }
        return binding.root
    }

    private fun getRestaurantDetail() {
        binding.progressBar.visibility = View.VISIBLE
        val client = ApiConfig.getApiService().getDetailRestaurant(idRestaurant)
        client.enqueue(object : Callback<DetailRestaurantResponse> {
            override fun onFailure(call: Call<DetailRestaurantResponse>, t: Throwable) {
                binding.progressBar.visibility = View.GONE
                Log.e(TAG, "onFailure: ${t.message}")
            }

            override fun onResponse(
                call: Call<DetailRestaurantResponse>,
                response: Response<DetailRestaurantResponse>
            ) {
                binding.progressBar.visibility = View.GONE
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        populateView(responseBody.restaurant)
                    }
                } else {
                    Log.e(TAG, "onResponse: ${response.message()}")
                }
            }
        })
    }

    private fun populateView(restaurant: Restaurant) {
        val imageRestaurant =
            "https://restaurant-api.dicoding.dev/images/large/${restaurant.pictureId}"
        Glide.with(requireContext())
            .load(imageRestaurant)
            .into(binding.ivResto)

        binding.tvNamaResto.text = restaurant.name
        binding.tvDeksripsi.text = restaurant.description

        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvReview.layoutManager = layoutManager

        val itemDecoration = DividerItemDecoration(requireContext(), layoutManager.orientation)
        binding.rvReview.addItemDecoration(itemDecoration)

        populateReview(restaurant.customerReviews)
        listFood.addAll(restaurant.menus.foods)

    }

    private fun populateReview(customerReviews: List<CustomerReviewsItem>) {
        val adapter = ReviewAdapter(customerReviews)
        binding.rvReview.adapter = adapter
    }

    companion object {
        private val TAG = HomeReviewFragment::class.java.simpleName
        var idRestaurant = ""
        var listFood = ArrayList<FoodsItem>()
    }
}