package com.example.latihanrestaurantapi.retrofit.response

import com.google.gson.annotations.SerializedName

data class ListRestoResponse(

	@field:SerializedName("count")
	val count: Int,

	@field:SerializedName("restaurants")
	val restaurants: List<RestaurantsItem>,

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String
)

data class SearchRestoResponse(

	@field:SerializedName("founded")
	val founded: Int,

	@field:SerializedName("restaurants")
	val restaurants: List<RestaurantsItem>,

	@field:SerializedName("error")
	val error: Boolean
)


data class RestaurantsItem(

	@field:SerializedName("pictureId")
	val pictureId: String,

	@field:SerializedName("city")
	val city: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("rating")
	val rating: Any,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("id")
	val id: String
)
