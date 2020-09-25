package com.wanandroid.model.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 * @author CuiZhen
 * @date 2020/3/21
 */
@Dao
interface ReadHistoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg mode: ReadHistoryModel)

    @Query("DELETE FROM ReadHistoryModel WHERE link = :link")
    suspend fun delete(link: String)

    @Query("DELETE FROM ReadHistoryModel")
    suspend fun deleteAll()

    @Query("SELECT * FROM ReadHistoryModel ORDER BY time DESC LIMIT (:page)*(:size), (:size)")
    suspend fun findAll(page: Int, size: Int): List<ReadHistoryModel>

}