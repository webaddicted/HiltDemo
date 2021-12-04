package com.webaddicted.newhiltproject.data.db

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.room.Database
import androidx.room.RoomDatabase
import com.webaddicted.newhiltproject.utils.constant.ApiConstant

@Database(entities = [TodayBeatEntity::class], version = ApiConstant.DB_VERSION,exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun todayBeatDao(): BeatDao
}