package com.example.lab09_products

import com.google.gson.annotations.SerializedName


data class ProductModel(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("category") val category: String,
    @SerializedName("price") val price: Double,
    @SerializedName("discountPercentage") val discountPercentage: Double,
    @SerializedName("rating") val rating: Double,
    @SerializedName("stock") val stock: Int,
    @SerializedName("tags") val tags: List<String>,
    @SerializedName("brand") val brand: String,
)

data class ProductResponse(
    @SerializedName("products") val products: List<ProductModel>
)
