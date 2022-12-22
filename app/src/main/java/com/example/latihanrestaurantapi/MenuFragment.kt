package com.example.latihanrestaurantapi

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.latihanrestaurantapi.adapter.MenuAdapter
import com.example.latihanrestaurantapi.databinding.FragmentMenuBinding


class MenuFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMenuBinding.inflate(inflater, container, false)

        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvMenu.layoutManager = layoutManager
        val adapter = MenuAdapter(HomeReviewFragment.listFood)
        binding.rvMenu.adapter = adapter

        return binding.root
    }
}