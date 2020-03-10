package com.rizalfahlepi.moviecatalogue.data.source.local

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import com.rizalfahlepi.moviecatalogue.data.source.local.entity.MovieEntity
import com.rizalfahlepi.moviecatalogue.data.source.local.entity.TVSeriesEntity
import com.rizalfahlepi.moviecatalogue.data.source.local.room.MovieCatalogueDao

class LocalDataSource private constructor(private val mMovieCatalogueDao: MovieCatalogueDao) {

    companion object {
        private var INSTANCE: LocalDataSource? = null

        fun getInstance(movieCatalogueDao: MovieCatalogueDao): LocalDataSource {
            if (INSTANCE == null) {
                INSTANCE = LocalDataSource(movieCatalogueDao)
            }
            return INSTANCE as LocalDataSource
        }
    }

    fun getAllMovies(): DataSource.Factory<Int, MovieEntity> = mMovieCatalogueDao.getMovies()

    fun getAllTVSeries(): DataSource.Factory<Int, TVSeriesEntity> = mMovieCatalogueDao.getTVSeries()

    fun getMovieByID(movieID: Int): LiveData<MovieEntity> = mMovieCatalogueDao.getMovieById(movieID)

    fun getTVSeriesByID(tvSeriesID: Int): LiveData<TVSeriesEntity> = mMovieCatalogueDao.getTVSeriesById(tvSeriesID)

    fun getFavoriteMovies(): DataSource.Factory<Int, MovieEntity> = mMovieCatalogueDao.getFavoriteMovie()

    fun getFavoriteTVSeries(): DataSource.Factory<Int, TVSeriesEntity> = mMovieCatalogueDao.getFavoriteTVSerie()

    fun insertMovies(movies: List<MovieEntity>) {
        mMovieCatalogueDao.insertMovies(movies)
    }

    fun insertMovie(movie: MovieEntity) {
        mMovieCatalogueDao.insertMovie(movie)
    }

    fun insertTVSeries(tvSeries: List<TVSeriesEntity>) {
        mMovieCatalogueDao.insertTVSeries(tvSeries)
    }

    fun insertTVSerie(tvSerie: TVSeriesEntity) {
        mMovieCatalogueDao.insertTVSerie(tvSerie)
    }

    fun setMovieFavorite(movie: MovieEntity, newState: Boolean) {
        movie.favorite = newState
        mMovieCatalogueDao.updateMovie(movie)
    }

    fun setTVSeriesFavorite(tvSeries: TVSeriesEntity, newState: Boolean) {
        tvSeries.favorite = newState
        mMovieCatalogueDao.updateTVSeries(tvSeries)
    }
}