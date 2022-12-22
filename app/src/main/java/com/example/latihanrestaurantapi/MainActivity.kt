package com.example.latihanrestaurantapi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.latihanrestaurantapi.adapter.RestaurantAdapter
import com.example.latihanrestaurantapi.databinding.ActivityMainBinding
import com.example.latihanrestaurantapi.retrofit.api.ApiConfig
import com.example.latihanrestaurantapi.retrofit.response.ListRestoResponse
import com.example.latihanrestaurantapi.retrofit.response.RestaurantsItem
import com.example.latihanrestaurantapi.retrofit.response.SearchRestoResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Madhang Resto"

        val layoutManager = LinearLayoutManager(this)
        binding.rvRestaurant.layoutManager = layoutManager
        showRestaurantData()

        binding.edtSearch.onSubmit { submit() }

    }

    private fun submit() {
        binding.progressBar.visibility = View.VISIBLE
        val client = ApiConfig.getApiService().searchResto(binding.edtSearch.text.toString())

        client.enqueue(object : Callback<SearchRestoResponse> {
            override fun onFailure(call: Call<SearchRestoResponse>, t: Throwable) {
                binding.progressBar.visibility = View.GONE
                showToast(t.message.toString())
            }

            override fun onResponse(
                call: Call<SearchRestoResponse>,
                response: Response<SearchRestoResponse>
            ) {
                binding.progressBar.visibility = View.GONE
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        val adapter = RestaurantAdapter(responseBody.restaurants)
                        binding.rvRestaurant.adapter = adapter
                    }
                } else {
                    showToast(response.message().toString())
                }
            }
        })
    }


    fun EditText.onSubmit(func: () -> Unit) {
        setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                func()
            }
            true
        }
    }


    private fun showRestaurantData() {
        val client = ApiConfig.getApiService().getRestaurantList()
        client.enqueue(object : Callback<ListRestoResponse> {
            override fun onFailure(call: Call<ListRestoResponse>, t: Throwable) {
                binding.progressBar.visibility = View.GONE
                showToast(t.message.toString())
            }

            override fun onResponse(
                call: Call<ListRestoResponse>,
                response: Response<ListRestoResponse>
            ) {
                binding.progressBar.visibility = View.GONE
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        val adapter = RestaurantAdapter(responseBody.restaurants)
                        binding.rvRestaurant.adapter = adapter
                    }
                } else {
                    showToast(response.message().toString())
                }
            }
        })

    }

    private fun showToast(msg: String) {
        Toast.makeText(this@MainActivity, msg, Toast.LENGTH_SHORT).show()
    }
}