package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_captions")
data class FavoriteEntity(
    @PrimaryKey val id: Int,
    val text: String,
    val categoryId: String,
    val categoryName: String,
    val addedAt: Long = System.currentTimeMillis()
)
