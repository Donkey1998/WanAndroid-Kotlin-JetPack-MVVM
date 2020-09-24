package com.wanandroid.model.db.dao

import androidx.room.Entity

/**
 * @author CuiZhen
 * @date 2020/3/21
 */
@Entity(primaryKeys = ["link"])
data class ReadHistoryModel(
        val link: String,
        val title: String,
        val time: String
)