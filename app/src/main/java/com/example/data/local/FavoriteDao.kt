package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM favorite_captions ORDER BY addedAt DESC")
    fun getAllFavorites(): Flow<List<FavoriteEntity>>

    @Query("SELECT id FROM favorite_captions")
    fun getAllFavoriteIds(): Flow<List<Int>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favorite: FavoriteEntity)

    @Query("DELETE FROM favorite_captions WHERE id = :id")
    suspend fun deleteFavorite(id: Int)

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_captions WHERE id = :id)")
    suspend fun isFavorite(id: Int): Boolean
}
