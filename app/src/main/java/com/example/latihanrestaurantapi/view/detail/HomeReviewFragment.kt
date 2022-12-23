package com.example.latihanrestaurantapi.view.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.latihanrestaurantapi.adapter.ReviewAdapter
import com.example.latihanrestaurantapi.databinding.FragmentHomeReviewBinding
import com.example.latihanrestaurantapi.retrofit.response.*
import com.example.latihanrestaurantapi.viewmodel.MainViewModel

class HomeReviewFragment : Fragment() {
    private lateinit var binding: FragmentHomeReviewBinding
    private lateinit var mainViewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeReviewBinding.inflate(inflater, container, false)
        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]

        mainViewModel.getRestaurantDetail()

        mainViewModel.restaurantDetail.observe(viewLifecycleOwner) {
            populateView(it)
        }

        mainViewModel.customerReview.observe(viewLifecycleOwner) {
            populateReview(it)
        }

        mainViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        binding.btnSend.setOnClickListener {
            sendReview()
        }
        return binding.root
    }

    private fun sendReview() {
        if (binding.edtReview.text.toString().isNotEmpty()) {
            mainViewModel.sendReview(binding.edtReview.text.toString())
        } else {
            binding.edtReview.error = "Field ini masih kosong"
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
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