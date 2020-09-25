package com.wanandroid.model.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.wanandroid.model.db.dao.ReadHistoryDao
import com.wanandroid.model.db.dao.ReadHistoryModel

/**
 * @author CuiZhen
 * @date 2020/3/21
 */
@Database(
        entities = [
            ReadHistoryModel::class
        ],
        version = 1,
        exportSchema = false
)
abstract class WanDatabase : RoomDatabase() {
    abstract fun readHistoryDao(): ReadHistoryDao
    companion object {
        @Volatile
        private var INSTANCE: WanDatabase? = null

        fun getInstance(context: Context): WanDatabase = INSTANCE
            ?: synchronized(this) {
            INSTANCE
                ?: buildDatabase(
                    context
                ).also { INSTANCE = it }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                WanDatabase::class.java, "WanDatabase.db")
                .build()
    }
}