package com.example.latihanrestaurantapi.view.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.latihanrestaurantapi.adapter.RestaurantAdapter
import com.example.latihanrestaurantapi.databinding.ActivityMainBinding
import com.example.latihanrestaurantapi.retrofit.response.RestaurantsItem
import com.example.latihanrestaurantapi.viewmodel.MainViewModel


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Madhang Resto"

        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]

        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        mainViewModel.listOfRestaurant.observe(this) {
            setRestaurantData(it)
        }


        val layoutManager = LinearLayoutManager(this)
        binding.rvRestaurant.layoutManager = layoutManager

        binding.edtSearch.onSubmit { mainViewModel.submit(binding.edtSearch.text.toString()) }

    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun setRestaurantData(listOfRestaurant: List<RestaurantsItem>) {
        val adapter = RestaurantAdapter(listOfRestaurant)
        binding.rvRestaurant.adapter = adapter
    }


    fun EditText.onSubmit(func: () -> Unit) {
        setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                func()
            }
            true
        }
    }

}