package com.rizalfahlepi.moviecatalogue.di

import android.content.Context
import com.rizalfahlepi.moviecatalogue.data.MovieCatalogueRepository
import com.rizalfahlepi.moviecatalogue.data.source.local.LocalDataSource
import com.rizalfahlepi.moviecatalogue.data.source.local.room.MovieCatalogueDatabase
import com.rizalfahlepi.moviecatalogue.data.source.remote.RemoteDataSource
import com.rizalfahlepi.moviecatalogue.utils.AppExecutors
import com.rizalfahlepi.moviecatalogue.utils.JsonHelper

object Injection {
    fun provideRepository(context: Context): MovieCatalogueRepository {

        val database = MovieCatalogueDatabase.getInstance(context)

        val remoteDataSource = RemoteDataSource.getInstance(JsonHelper())
        val localDataSource = LocalDataSource.getInstance(database.movieCatalogueDao())
        val appExecutors = AppExecutors()

        return MovieCatalogueRepository.getInstance(remoteDataSource, localDataSource, appExecutors)
    }
}