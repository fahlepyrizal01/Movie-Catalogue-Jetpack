package com.rizalfahlepi.moviecatalogue.ui.activity.detailmovie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.rizalfahlepi.moviecatalogue.data.MovieCatalogueRepository
import com.rizalfahlepi.moviecatalogue.data.source.local.entity.MovieEntity
import com.rizalfahlepi.moviecatalogue.data.source.local.entity.TVSeriesEntity
import com.rizalfahlepi.moviecatalogue.vo.Resource

class DetailMovieViewModel(private val movieCatalogueRepository: MovieCatalogueRepository): ViewModel() {

    private val dataID = MutableLiveData<Int>()

    fun setSelectedData(dataID: Int) {
        this.dataID.value = dataID
    }

    var movie: LiveData<Resource<MovieEntity>> = Transformations.switchMap(dataID) {
        movieCatalogueRepository.getMovieDetail(it)
    }

    var tvSeries: LiveData<Resource<TVSeriesEntity>> = Transformations.switchMap(dataID) {
        movieCatalogueRepository.getTVSeriesDetail(it)
    }

    fun setFavoriteMovie() {
        val movieResource = movie.value
        if (movieResource != null) {
            val movie = movieResource.data

            if (movie != null) {
                val movieEntity = movie
                val newState = !movieEntity.favorite
                movieCatalogueRepository.setMovieFavorite(movieEntity, newState)
            }
        }
    }

    fun setFavoriteTVSeries() {
        val tvSeriesResource = tvSeries.value
        if (tvSeriesResource != null) {
            val tvSeries = tvSeriesResource.data

            if (tvSeries != null) {
                val tvSeriesEntity = tvSeries
                val newState = !tvSeriesEntity.favorite
                movieCatalogueRepository.setTVSeriesFavorite(tvSeriesEntity, newState)
            }
        }
    }
}