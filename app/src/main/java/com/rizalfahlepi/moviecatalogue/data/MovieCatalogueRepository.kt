package com.rizalfahlepi.moviecatalogue.data

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.rizalfahlepi.moviecatalogue.data.source.local.LocalDataSource
import com.rizalfahlepi.moviecatalogue.data.source.local.entity.MovieEntity
import com.rizalfahlepi.moviecatalogue.data.source.local.entity.TVSeriesEntity
import com.rizalfahlepi.moviecatalogue.data.source.remote.ApiResponse
import com.rizalfahlepi.moviecatalogue.data.source.remote.RemoteDataSource
import com.rizalfahlepi.moviecatalogue.data.source.remote.response.MovieResponse
import com.rizalfahlepi.moviecatalogue.data.source.remote.response.TVSeriesResponse
import com.rizalfahlepi.moviecatalogue.utils.AppExecutors
import com.rizalfahlepi.moviecatalogue.vo.Resource

class MovieCatalogueRepository private constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : MovieCatalogueDataSource {

    companion object {
        @Volatile
        private var instance: MovieCatalogueRepository? = null

        fun getInstance(remoteData: RemoteDataSource, localData: LocalDataSource, appExecutors: AppExecutors): MovieCatalogueRepository =
            instance ?: synchronized(this) {
                instance ?: MovieCatalogueRepository(remoteData, localData, appExecutors)
            }
    }

    override fun getAllMovies(): LiveData<Resource<PagedList<MovieEntity>>> {
        return object : NetworkBoundResource<PagedList<MovieEntity>, List<MovieResponse>>(appExecutors) {
            public override fun loadFromDB(): LiveData<PagedList<MovieEntity>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(4)
                    .setPageSize(4)
                    .build()
                return LivePagedListBuilder(localDataSource.getAllMovies(), config).build()
            }

            override fun shouldFetch(data: PagedList<MovieEntity>?): Boolean = data == null || data.isEmpty()

            public override fun createCall(): LiveData<ApiResponse<List<MovieResponse>>> = remoteDataSource.getAllMovie()

            public override fun saveCallResult(data: List<MovieResponse>) {
                val movieList = ArrayList<MovieEntity>()
                for (response in data) {
                    val movie = MovieEntity(
                        response.id,
                        response.title,
                        response.releaseDate,
                        response.popularity,
                        response.voteCount,
                        response.posterPath,
                        response.originalLanguage,
                        response.voteAverage,
                        response.overview,
                        false)
                    movieList.add(movie)
                }

                localDataSource.insertMovies(movieList)
            }
        }.asLiveData()
    }

    override fun getAllTVSeries(): LiveData<Resource<PagedList<TVSeriesEntity>>> {
        return object : NetworkBoundResource<PagedList<TVSeriesEntity>, List<TVSeriesResponse>>(appExecutors) {
            public override fun loadFromDB(): LiveData<PagedList<TVSeriesEntity>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(4)
                    .setPageSize(4)
                    .build()
                return LivePagedListBuilder(localDataSource.getAllTVSeries(), config).build()
            }

            override fun shouldFetch(data: PagedList<TVSeriesEntity>?): Boolean = data == null || data.isEmpty()

            public override fun createCall(): LiveData<ApiResponse<List<TVSeriesResponse>>> = remoteDataSource.getAllTVSeries()

            public override fun saveCallResult(data: List<TVSeriesResponse>) {
                val tvSeriesList = ArrayList<TVSeriesEntity>()
                for (response in data) {
                    val tvSeries = TVSeriesEntity(
                        response.id,
                        response.name,
                        response.firstAirDate,
                        response.popularity,
                        response.voteCount,
                        response.posterPath,
                        response.originalLanguage,
                        response.voteAverage,
                        response.overview,
                        false)
                    tvSeriesList.add(tvSeries)
                }

                localDataSource.insertTVSeries(tvSeriesList)
            }
        }.asLiveData()
    }

    override fun getMovieDetail(movieID: Int): LiveData<Resource<MovieEntity>> {
        return object : NetworkBoundResource<MovieEntity, MovieResponse>(appExecutors) {
            override fun loadFromDB(): LiveData<MovieEntity> = localDataSource.getMovieByID(movieID)

            override fun shouldFetch(data: MovieEntity?): Boolean = data?.id == null

            override fun createCall(): LiveData<ApiResponse<MovieResponse>> = remoteDataSource.getMovie(movieID)

            override fun saveCallResult(data: MovieResponse) {
                val movie = MovieEntity(
                    data.id,
                    data.title,
                    data.releaseDate,
                    data.popularity,
                    data.voteCount,
                    data.posterPath,
                    data.originalLanguage,
                    data.voteAverage,
                    data.overview,
                    false)

                localDataSource.insertMovie(movie)
            }
        }.asLiveData()
    }

    override fun getTVSeriesDetail(tvSeriesID: Int): LiveData<Resource<TVSeriesEntity>> {
        return object : NetworkBoundResource<TVSeriesEntity, TVSeriesResponse>(appExecutors) {
            override fun loadFromDB(): LiveData<TVSeriesEntity> = localDataSource.getTVSeriesByID(tvSeriesID)

            override fun shouldFetch(data: TVSeriesEntity?): Boolean = data?.id == null

            override fun createCall(): LiveData<ApiResponse<TVSeriesResponse>> = remoteDataSource.getTVSeries(tvSeriesID)

            override fun saveCallResult(data: TVSeriesResponse) {
                val tvSeries = TVSeriesEntity(
                    data.id,
                    data.name,
                    data.firstAirDate,
                    data.popularity,
                    data.voteCount,
                    data.posterPath,
                    data.originalLanguage,
                    data.voteAverage,
                    data.overview,
                    false)

                localDataSource.insertTVSerie(tvSeries)
            }
        }.asLiveData()
    }

    override fun getFavoriteMovies(): LiveData<PagedList<MovieEntity>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(4)
            .setPageSize(4)
            .build()
        return LivePagedListBuilder(localDataSource.getFavoriteMovies(), config).build()
    }

    override fun getFavoriteTVSeries(): LiveData<PagedList<TVSeriesEntity>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(4)
            .setPageSize(4)
            .build()
        return LivePagedListBuilder(localDataSource.getFavoriteTVSeries(), config).build()
    }

    override fun setMovieFavorite(movie: MovieEntity, state: Boolean) {
        return appExecutors.diskIO().execute { localDataSource.setMovieFavorite(movie, state)}
    }

    override fun setTVSeriesFavorite(tvSeries: TVSeriesEntity, state: Boolean) {
        return appExecutors.diskIO().execute { localDataSource.setTVSeriesFavorite(tvSeries, state)}
    }
}