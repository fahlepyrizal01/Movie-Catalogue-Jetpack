package com.rizalfahlepi.moviecatalogue.data.source.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rizalfahlepi.moviecatalogue.data.source.remote.response.MovieResponse
import com.rizalfahlepi.moviecatalogue.data.source.remote.response.TVSeriesResponse
import com.rizalfahlepi.moviecatalogue.utils.EspressoIdlingResource
import com.rizalfahlepi.moviecatalogue.utils.JsonHelper

class RemoteDataSource private constructor(private val jsonHelper: JsonHelper) {

    companion object {
        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(helper: JsonHelper): RemoteDataSource =
            instance ?: synchronized(this) {
                instance ?: RemoteDataSource(helper)
            }
    }

    fun getAllMovie(): LiveData<ApiResponse<List<MovieResponse>>> {
        EspressoIdlingResource.increment()
        val resultMovies = MutableLiveData<ApiResponse<List<MovieResponse>>>()
        jsonHelper.loadMovieList {
            resultMovies.value = ApiResponse.success(it)
            EspressoIdlingResource.decrement()
        }
        return resultMovies
    }

    fun getAllTVSeries(): LiveData<ApiResponse<List<TVSeriesResponse>>> {
        EspressoIdlingResource.increment()
        val resultTVSeries = MutableLiveData<ApiResponse<List<TVSeriesResponse>>>()
        jsonHelper.loadTVSeriesList {
            resultTVSeries.value = ApiResponse.success(it)
            EspressoIdlingResource.decrement()
        }
        return resultTVSeries
    }

    fun getMovie(movieID: Int): LiveData<ApiResponse<MovieResponse>> {
        EspressoIdlingResource.increment()
        val resultMovie = MutableLiveData<ApiResponse<MovieResponse>>()
        jsonHelper.loadMovieDetail(movieID){
            resultMovie.value = ApiResponse.success(it)
            EspressoIdlingResource.decrement()
        }
        return resultMovie
    }

    fun getTVSeries(tvSeriesID: Int): LiveData<ApiResponse<TVSeriesResponse>> {
        EspressoIdlingResource.increment()
        val resultTVSeries = MutableLiveData<ApiResponse<TVSeriesResponse>>()
        jsonHelper.loadTVSeriesDetail(tvSeriesID){
            resultTVSeries.value = ApiResponse.success(it)
            EspressoIdlingResource.decrement()
        }
        return resultTVSeries
    }
}