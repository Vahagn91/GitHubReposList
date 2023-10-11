package com.example.sololearntesttask.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CachedDataDao {

    @Query("SELECT * FROM cached_data WHERE id = :dataId")
    fun getCachedData(dataId: String): CachedDataEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrUpdateCachedData(cachedData: CachedDataEntity)
}