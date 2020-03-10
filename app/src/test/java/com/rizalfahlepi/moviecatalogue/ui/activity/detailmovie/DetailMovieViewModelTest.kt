package com.rizalfahlepi.moviecatalogue.ui.activity.detailmovie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.rizalfahlepi.moviecatalogue.data.MovieCatalogueRepository
import com.rizalfahlepi.moviecatalogue.data.source.local.entity.MovieEntity
import com.rizalfahlepi.moviecatalogue.data.source.local.entity.TVSeriesEntity
import com.rizalfahlepi.moviecatalogue.utils.DataDummy
import com.rizalfahlepi.moviecatalogue.vo.Resource
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DetailMovieViewModelTest {
    private lateinit var viewModelMovie: DetailMovieViewModel
    private lateinit var viewModelTVSeries: DetailMovieViewModel
    private val dummyMovie = DataDummy.generateDummyMovies()[0]
    private val movieID = dummyMovie.id
    private val dummyTVSeries = DataDummy.generateDummyTVSeries()[0]
    private val tvSeriesID = dummyTVSeries.id

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var movieCatalogueRepository: MovieCatalogueRepository

    @Mock
    private lateinit var observerMovie: Observer<Resource<MovieEntity>>

    @Mock
    private lateinit var observerTVSeries: Observer<Resource<TVSeriesEntity>>

    @Before
    fun setUp() {
        viewModelMovie = DetailMovieViewModel(movieCatalogueRepository)
        viewModelMovie.setSelectedData(movieID ?: 0)
        viewModelTVSeries = DetailMovieViewModel(movieCatalogueRepository)
        viewModelTVSeries.setSelectedData(tvSeriesID ?: 0)
    }

    @Test
    fun getMovie() {
        val dummyMovie = Resource.success(DataDummy.generateDummyMovies()[0])
        val movie = MutableLiveData<Resource<MovieEntity>>()
        movie.value = dummyMovie

        `when`<LiveData<Resource<MovieEntity>>>(movieCatalogueRepository.getMovieDetail(movieID ?: 0)).thenReturn(movie)

        viewModelMovie.movie.observeForever(observerMovie)
        verify(observerMovie).onChanged(dummyMovie)
    }

    @Test
    fun getTVSeries() {
        val dummyTVSeries = Resource.success(DataDummy.generateDummyTVSeries()[0])
        val tvSeries = MutableLiveData<Resource<TVSeriesEntity>>()
        tvSeries.value = dummyTVSeries

        `when`<LiveData<Resource<TVSeriesEntity>>>(movieCatalogueRepository.getTVSeriesDetail(tvSeriesID ?: 0)).thenReturn(tvSeries)

        viewModelTVSeries.tvSeries.observeForever(observerTVSeries)
        verify(observerTVSeries).onChanged(dummyTVSeries)
    }
}