package com.example.sololearntesttask.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CachedDataEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cachedDataDao(): CachedDataDao
}