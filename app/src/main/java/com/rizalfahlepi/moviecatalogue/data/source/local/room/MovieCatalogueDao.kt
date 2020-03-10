package com.rizalfahlepi.moviecatalogue.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import com.rizalfahlepi.moviecatalogue.data.source.local.entity.MovieEntity
import com.rizalfahlepi.moviecatalogue.data.source.local.entity.TVSeriesEntity

@Dao
interface MovieCatalogueDao {

    @Query("SELECT * FROM movieentities")
    fun getMovies(): DataSource.Factory<Int, MovieEntity>

    @Query("SELECT * FROM tvseriesentities")
    fun getTVSeries(): DataSource.Factory<Int, TVSeriesEntity>

    @Query("SELECT * FROM movieentities WHERE id = :movieID")
    fun getMovieById(movieID: Int): LiveData<MovieEntity>

    @Query("SELECT * FROM tvseriesentities WHERE id = :tvSeriesID")
    fun getTVSeriesById(tvSeriesID: Int): LiveData<TVSeriesEntity>

    @Query("SELECT * FROM movieentities where favorite = 1")
    fun getFavoriteMovie(): DataSource.Factory<Int, MovieEntity>

    @Query("SELECT * FROM tvseriesentities where favorite = 1")
    fun getFavoriteTVSerie(): DataSource.Factory<Int, TVSeriesEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovies(movies: List<MovieEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(movie: MovieEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTVSeries(tvSeries: List<TVSeriesEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTVSerie(tvSerie: TVSeriesEntity)

    @Update
    fun updateMovie(movie: MovieEntity)

    @Update
    fun updateTVSeries(tvSeries: TVSeriesEntity)
}