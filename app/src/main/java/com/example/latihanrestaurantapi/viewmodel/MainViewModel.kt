package com.example.latihanrestaurantapi.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.latihanrestaurantapi.view.detail.HomeReviewFragment
import com.example.latihanrestaurantapi.retrofit.api.ApiConfig
import com.example.latihanrestaurantapi.retrofit.response.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private var _listOfRestaurant = MutableLiveData<List<RestaurantsItem>>()
    val listOfRestaurant: LiveData<List<RestaurantsItem>> = _listOfRestaurant

    private var _restaurantDetail = MutableLiveData<Restaurant>()
    val restaurantDetail: LiveData<Restaurant> = _restaurantDetail

    private var _customerReview = MutableLiveData<List<CustomerReviewsItem>>()
    val customerReview: LiveData<List<CustomerReviewsItem>> = _customerReview

    private val _menuRestaurant = MutableLiveData<List<FoodsItem>>()
    val menuRestaurant: LiveData<List<FoodsItem>> = _menuRestaurant

    init {
        showRestaurantData()
    }

    fun showRestaurantData() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getRestaurantList()
        client.enqueue(object : Callback<ListRestoResponse> {
            override fun onFailure(call: Call<ListRestoResponse>, t: Throwable) {
                _isLoading.value = false
            }

            override fun onResponse(
                call: Call<ListRestoResponse>,
                response: Response<ListRestoResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _listOfRestaurant.value = responseBody.restaurants
                    }
                } else {
                    Log.e(TAG, "onResponse: ${response.message()}")
                }
            }
        })

    }

    fun submit(search: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().searchResto(search)

        client.enqueue(object : Callback<SearchRestoResponse> {
            override fun onFailure(call: Call<SearchRestoResponse>, t: Throwable) {
                _isLoading.value = false
            }

            override fun onResponse(
                call: Call<SearchRestoResponse>,
                response: Response<SearchRestoResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _listOfRestaurant.value = responseBody.restaurants
                    }
                }
            }
        })
    }


    //Review and food list

    fun getRestaurantDetail() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailRestaurant(HomeReviewFragment.idRestaurant)
        client.enqueue(object : Callback<DetailRestaurantResponse> {
            override fun onFailure(call: Call<DetailRestaurantResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }

            override fun onResponse(
                call: Call<DetailRestaurantResponse>,
                response: Response<DetailRestaurantResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _restaurantDetail.value = responseBody.restaurant
                    }
                } else {
                    Log.e(TAG, "onResponse: ${response.message()}")
                }
            }
        })
    }

    fun sendReview(review: String) {

        val client = ApiConfig.getApiService()
            .postReview(
                HomeReviewFragment.idRestaurant,
                "Achmad Syarif",
                review
            )

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
                        _customerReview.value =
                            responseBody.customerReviews as List<CustomerReviewsItem>
                    }
                }
            }
        })
    }

    companion object {
        private val TAG = "MainViewModel"
    }
}