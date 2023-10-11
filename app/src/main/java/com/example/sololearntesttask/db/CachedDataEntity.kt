package com.example.sololearntesttask.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cached_data")
data class CachedDataEntity(
    @PrimaryKey val id: String,
    val data: String
)