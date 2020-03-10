package com.rizalfahlepi.moviecatalogue.data.source.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.rizalfahlepi.moviecatalogue.data.source.local.entity.MovieEntity
import com.rizalfahlepi.moviecatalogue.data.source.local.entity.TVSeriesEntity

@Database(entities = [MovieEntity::class, TVSeriesEntity::class], version = 1, exportSchema = false)
abstract class MovieCatalogueDatabase : RoomDatabase() {
    abstract fun movieCatalogueDao(): MovieCatalogueDao

    companion object {

        @Volatile
        private var INSTANCE: MovieCatalogueDatabase? = null

        fun getInstance(context: Context): MovieCatalogueDatabase {
            if (INSTANCE == null) {
                synchronized(MovieCatalogueDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(context.applicationContext,
                            MovieCatalogueDatabase::class.java, "Movies.db")
                            .build()
                    }
                }
            }
            return INSTANCE as MovieCatalogueDatabase
        }
    }
}