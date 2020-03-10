package com.rizalfahlepi.moviecatalogue.ui.fragment.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.rizalfahlepi.moviecatalogue.data.MovieCatalogueRepository
import com.rizalfahlepi.moviecatalogue.data.source.local.entity.MovieEntity
import com.rizalfahlepi.moviecatalogue.data.source.local.entity.TVSeriesEntity
import com.rizalfahlepi.moviecatalogue.vo.Resource

class MovieViewModel(private val movieCatalogueRepository: MovieCatalogueRepository): ViewModel() {
    fun getMovies(): LiveData<Resource<PagedList<MovieEntity>>> = movieCatalogueRepository.getAllMovies()
    fun getTVSeries(): LiveData<Resource<PagedList<TVSeriesEntity>>> = movieCatalogueRepository.getAllTVSeries()
    fun getFavoriteMovies(): LiveData<PagedList<MovieEntity>> = movieCatalogueRepository.getFavoriteMovies()
    fun getFavoriteTVSeries(): LiveData<PagedList<TVSeriesEntity>> = movieCatalogueRepository.getFavoriteTVSeries()

    fun setFavoriteMovies(movieEntity: MovieEntity) {
        val newState = !movieEntity.favorite
        movieCatalogueRepository.setMovieFavorite(movieEntity, newState)
    }

    fun setFavoriteTVSeries(tvSeriesEntity: TVSeriesEntity) {
        val newState = !tvSeriesEntity.favorite
        movieCatalogueRepository.setTVSeriesFavorite(tvSeriesEntity, newState)
    }
}