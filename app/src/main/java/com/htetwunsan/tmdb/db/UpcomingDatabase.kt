package com.htetwunsan.tmdb.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.htetwunsan.tmdb.model.IntListConverter
import com.htetwunsan.tmdb.model.UpcomingMovie

@Database(
    entities = [UpcomingMovie::class, UpcomingRemoteKeys::class],
    version = 1,
    exportSchema = false
)
abstract class UpcomingDatabase : RoomDatabase() {
    abstract fun upcomingDao(): UpcomingDao
    abstract fun upcomingRemoteKeysDao(): UpcomingRemoteKeysDao

    companion object {

        @Volatile
        private var INSTANCE: UpcomingDatabase? = null

        fun getInstance(context: Context): UpcomingDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE
                    ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                UpcomingDatabase::class.java, "Tmdb.db")
                .addTypeConverter(IntListConverter())
                .build()
    }
}