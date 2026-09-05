package com.example.data.model

data class Category(
    val id: String,
    val name: String,
    val iconEmoji: String,
    val description: String = ""
)

data class Caption(
    val id: Int,
    val text: String,
    val categoryId: String,
    val categoryName: String,
    val isPopular: Boolean = false
)
