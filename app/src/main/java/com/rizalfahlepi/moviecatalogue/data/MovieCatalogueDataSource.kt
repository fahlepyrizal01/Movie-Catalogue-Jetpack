package com.rizalfahlepi.moviecatalogue.data

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.rizalfahlepi.moviecatalogue.data.source.local.entity.MovieEntity
import com.rizalfahlepi.moviecatalogue.data.source.local.entity.TVSeriesEntity
import com.rizalfahlepi.moviecatalogue.vo.Resource

interface MovieCatalogueDataSource {

    fun getAllMovies(): LiveData<Resource<PagedList<MovieEntity>>>

    fun getAllTVSeries(): LiveData<Resource<PagedList<TVSeriesEntity>>>

    fun getMovieDetail(movieID: Int): LiveData<Resource<MovieEntity>>

    fun getTVSeriesDetail(tvSeriesID: Int): LiveData<Resource<TVSeriesEntity>>

    fun getFavoriteMovies(): LiveData<PagedList<MovieEntity>>

    fun getFavoriteTVSeries(): LiveData<PagedList<TVSeriesEntity>>

    fun setMovieFavorite(movie: MovieEntity, state: Boolean)

    fun setTVSeriesFavorite(tvSeries: TVSeriesEntity, state: Boolean)
}