package com.rizalfahlepi.moviecatalogue.ui.fragment.movie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.rizalfahlepi.moviecatalogue.data.MovieCatalogueRepository
import com.rizalfahlepi.moviecatalogue.data.source.local.entity.MovieEntity
import com.rizalfahlepi.moviecatalogue.data.source.local.entity.TVSeriesEntity
import com.rizalfahlepi.moviecatalogue.vo.Resource
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.*
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MovieViewModelTest {

    private lateinit var viewModel: MovieViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var movieCatalogueRepository: MovieCatalogueRepository

    @Mock
    private lateinit var observerMovie: Observer<Resource<PagedList<MovieEntity>>>

    @Mock
    private lateinit var observerTVSeries: Observer<Resource<PagedList<TVSeriesEntity>>>

    @Mock
    private lateinit var observerFavoriteMovie: Observer<PagedList<MovieEntity>>

    @Mock
    private lateinit var observerFavoriteTVSeries: Observer<PagedList<TVSeriesEntity>>


    @Mock
    private lateinit var pagedListMovie: PagedList<MovieEntity>

    @Mock
    private lateinit var pagedListTVSeries: PagedList<TVSeriesEntity>

    @Before
    fun setUp() {
        viewModel = MovieViewModel(movieCatalogueRepository)
    }

    @Test
    fun getMovies() {
        val dummyMovies = Resource.success(pagedListMovie)
        `when`(dummyMovies.data?.size).thenReturn(5)
        val movies = MutableLiveData<Resource<PagedList<MovieEntity>>>()
        movies.value = dummyMovies

        `when`(movieCatalogueRepository.getAllMovies()).thenReturn(movies)
        val movieEntities = viewModel.getMovies().value?.data
        verify(movieCatalogueRepository).getAllMovies()

        assertNotNull(movieEntities)
        assertEquals(5, movieEntities?.size)

        viewModel.getMovies().observeForever(observerMovie)
        verify(observerMovie).onChanged(dummyMovies)
    }

    @Test
    fun getTVSeries() {
        val dummyTVSeries = Resource.success(pagedListTVSeries)
        `when`(dummyTVSeries.data?.size).thenReturn(5)
        val tvSeries = MutableLiveData<Resource<PagedList<TVSeriesEntity>>>()
        tvSeries.value = dummyTVSeries

        `when`(movieCatalogueRepository.getAllTVSeries()).thenReturn(tvSeries)
        val tvSeriesEntities = viewModel.getTVSeries().value?.data
        verify(movieCatalogueRepository).getAllTVSeries()

        assertNotNull(tvSeriesEntities)
        assertEquals(5, tvSeriesEntities?.size)

        viewModel.getTVSeries().observeForever(observerTVSeries)
        verify(observerTVSeries).onChanged(dummyTVSeries)
    }

    @Test
    fun getFavoriteMovies() {
        val dummyMovies = pagedListMovie
        `when`(dummyMovies.size).thenReturn(5)
        val movies = MutableLiveData<PagedList<MovieEntity>>()
        movies.value = dummyMovies

        `when`(movieCatalogueRepository.getFavoriteMovies()).thenReturn(movies)
        val movieEntities = viewModel.getFavoriteMovies().value
        verify<MovieCatalogueRepository>(movieCatalogueRepository).getFavoriteMovies()

        assertNotNull(movieEntities)
        assertEquals(5, movieEntities?.size)

        viewModel.getFavoriteMovies().observeForever(observerFavoriteMovie)
        verify(observerFavoriteMovie).onChanged(dummyMovies)
    }

    @Test
    fun getFavoriteTVSeries() {
        val dummyTVSeries = pagedListTVSeries
        `when`(dummyTVSeries.size).thenReturn(5)
        val tvSeries = MutableLiveData<PagedList<TVSeriesEntity>>()
        tvSeries.value = dummyTVSeries

        `when`(movieCatalogueRepository.getFavoriteTVSeries()).thenReturn(tvSeries)
        val tvSeriesEntities = viewModel.getFavoriteTVSeries().value
        verify<MovieCatalogueRepository>(movieCatalogueRepository).getFavoriteTVSeries()

        assertNotNull(tvSeriesEntities)
        assertEquals(5, tvSeriesEntities?.size)

        viewModel.getFavoriteTVSeries().observeForever(observerFavoriteTVSeries)
        verify(observerFavoriteTVSeries).onChanged(dummyTVSeries)
    }
}