package com.rizalfahlepi.moviecatalogue.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.nhaarman.mockitokotlin2.verify
import com.rizalfahlepi.moviecatalogue.data.source.local.LocalDataSource
import com.rizalfahlepi.moviecatalogue.data.source.local.entity.MovieEntity
import com.rizalfahlepi.moviecatalogue.data.source.local.entity.TVSeriesEntity
import com.rizalfahlepi.moviecatalogue.data.source.remote.RemoteDataSource
import com.rizalfahlepi.moviecatalogue.utils.AppExecutors
import com.rizalfahlepi.moviecatalogue.utils.DataDummy
import com.rizalfahlepi.moviecatalogue.utils.LiveDataTestUtil
import com.rizalfahlepi.moviecatalogue.utils.PagedListUtil
import com.rizalfahlepi.moviecatalogue.vo.Resource
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`

class MovieCatalogueRepositoryTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val remote = Mockito.mock(RemoteDataSource::class.java)
    private val local = Mockito.mock(LocalDataSource::class.java)
    private val appExecutors = Mockito.mock(AppExecutors::class.java)

    private val movieCatalogueRepository =
        FakeMovieCatalogueRepository(
            remote,
            local,
            appExecutors
        )

    private val movieResponses = DataDummy.generateRemoteDummyMovies()
    private val movieID = movieResponses[0].id
    private val tvSeriesResponses = DataDummy.generateRemoteDummyTVSeries()
    private val tvSeriesID = tvSeriesResponses[0].id

    @Test
    fun getAllMovies() {
        @Suppress("UNCHECKED_CAST")
        val dataSourceFactory = Mockito.mock(DataSource.Factory::class.java) as DataSource.Factory<Int, MovieEntity>
        `when`(local.getAllMovies()).thenReturn(dataSourceFactory)
        movieCatalogueRepository.getAllMovies()

        val movieEntities = Resource.success(PagedListUtil.mockPagedList(DataDummy.generateDummyMovies()))
        verify(local).getAllMovies()

        assertNotNull(movieEntities.data)
        assertNotNull(movieEntities.data?.get(0)?.id)
        assertNotNull(movieEntities.data?.get(0)?.title)
        assertNotNull(movieEntities.data?.get(0)?.originalLanguage)
        assertNotNull(movieEntities.data?.get(0)?.overview)
        assertNotNull(movieEntities.data?.get(0)?.popularity)
        assertNotNull(movieEntities.data?.get(0)?.posterPath)
        assertNotNull(movieEntities.data?.get(0)?.releaseDate)
        assertNotNull(movieEntities.data?.get(0)?.voteAverage)
        assertNotNull(movieEntities.data?.get(0)?.voteCount)

        assertEquals(movieResponses.size.toLong(), movieEntities.data?.size?.toLong())
        assertEquals(movieResponses[0].id, movieEntities.data?.get(0)?.id)
        assertEquals(movieResponses[0].title, movieEntities.data?.get(0)?.title)
        assertEquals(movieResponses[0].originalLanguage, movieEntities.data?.get(0)?.originalLanguage)
        assertEquals(movieResponses[0].overview, movieEntities.data?.get(0)?.overview)
        assertEquals(movieResponses[0].popularity, movieEntities.data?.get(0)?.popularity)
        assertEquals(movieResponses[0].posterPath, movieEntities.data?.get(0)?.posterPath)
        assertEquals(movieResponses[0].releaseDate, movieEntities.data?.get(0)?.releaseDate)
        assertEquals(movieResponses[0].voteAverage, movieEntities.data?.get(0)?.voteAverage)
        assertEquals(movieResponses[0].voteCount, movieEntities.data?.get(0)?.voteCount)
    }

    @Test
    fun getAllTVSeries() {
        @Suppress("UNCHECKED_CAST")
        val dataSourceFactory = Mockito.mock(DataSource.Factory::class.java) as DataSource.Factory<Int, TVSeriesEntity>
        `when`(local.getAllTVSeries()).thenReturn(dataSourceFactory)
        movieCatalogueRepository.getAllTVSeries()

        val tvSeriesEntities = Resource.success(PagedListUtil.mockPagedList(DataDummy.generateDummyTVSeries()))
        verify(local).getAllTVSeries()

        assertNotNull(tvSeriesEntities.data)
        assertNotNull(tvSeriesEntities.data?.get(0)?.id)
        assertNotNull(tvSeriesEntities.data?.get(0)?.name)
        assertNotNull(tvSeriesEntities.data?.get(0)?.originalLanguage)
        assertNotNull(tvSeriesEntities.data?.get(0)?.overview)
        assertNotNull(tvSeriesEntities.data?.get(0)?.popularity)
        assertNotNull(tvSeriesEntities.data?.get(0)?.posterPath)
        assertNotNull(tvSeriesEntities.data?.get(0)?.firstAirDate)
        assertNotNull(tvSeriesEntities.data?.get(0)?.voteAverage)
        assertNotNull(tvSeriesEntities.data?.get(0)?.voteCount)

        assertEquals(tvSeriesResponses.size.toLong(), tvSeriesEntities.data?.size?.toLong())
        assertEquals(tvSeriesResponses[0].id, tvSeriesEntities.data?.get(0)?.id)
        assertEquals(tvSeriesResponses[0].name, tvSeriesEntities.data?.get(0)?.name)
        assertEquals(tvSeriesResponses[0].originalLanguage, tvSeriesEntities.data?.get(0)?.originalLanguage)
        assertEquals(tvSeriesResponses[0].overview, tvSeriesEntities.data?.get(0)?.overview)
        assertEquals(tvSeriesResponses[0].popularity, tvSeriesEntities.data?.get(0)?.popularity)
        assertEquals(tvSeriesResponses[0].posterPath, tvSeriesEntities.data?.get(0)?.posterPath)
        assertEquals(tvSeriesResponses[0].firstAirDate, tvSeriesEntities.data?.get(0)?.firstAirDate)
        assertEquals(tvSeriesResponses[0].voteAverage, tvSeriesEntities.data?.get(0)?.voteAverage)
        assertEquals(tvSeriesResponses[0].voteCount, tvSeriesEntities.data?.get(0)?.voteCount)
    }


    @Test
    fun getDetailMovie() {
        val dummyEntity = MutableLiveData<MovieEntity>()
        dummyEntity.value = DataDummy.generateDummyMovies()[0]
        `when`<LiveData<MovieEntity>>(local.getMovieByID(movieID ?: 0)).thenReturn(dummyEntity)

        val movieEntities = LiveDataTestUtil.getValue(movieCatalogueRepository.getMovieDetail(movieID ?: 0))
        verify(local).getMovieByID(movieID ?: 0)

        assertNotNull(movieEntities.data)
        assertNotNull(movieEntities.data?.id)
        assertNotNull(movieEntities.data?.title)
        assertNotNull(movieEntities.data?.originalLanguage)
        assertNotNull(movieEntities.data?.overview)
        assertNotNull(movieEntities.data?.popularity)
        assertNotNull(movieEntities.data?.posterPath)
        assertNotNull(movieEntities.data?.releaseDate)
        assertNotNull(movieEntities.data?.voteAverage)
        assertNotNull(movieEntities.data?.voteCount)

        assertEquals(movieResponses[0].id, movieEntities.data?.id)
        assertEquals(movieResponses[0].title, movieEntities.data?.title)
        assertEquals(movieResponses[0].originalLanguage, movieEntities.data?.originalLanguage)
        assertEquals(movieResponses[0].overview, movieEntities.data?.overview)
        assertEquals(movieResponses[0].popularity, movieEntities.data?.popularity)
        assertEquals(movieResponses[0].posterPath, movieEntities.data?.posterPath)
        assertEquals(movieResponses[0].releaseDate, movieEntities.data?.releaseDate)
        assertEquals(movieResponses[0].voteAverage, movieEntities.data?.voteAverage)
        assertEquals(movieResponses[0].voteCount, movieEntities.data?.voteCount)
    }

    @Test
    fun getDetailTVSeries() {
        val dummyEntity = MutableLiveData<TVSeriesEntity>()
        dummyEntity.value = DataDummy.generateDummyTVSeries()[0]
        `when`<LiveData<TVSeriesEntity>>(local.getTVSeriesByID(tvSeriesID ?: 0)).thenReturn(dummyEntity)

        val tvSeriesEntities = LiveDataTestUtil.getValue(movieCatalogueRepository.getTVSeriesDetail(tvSeriesID ?: 0))
        verify(local).getTVSeriesByID(tvSeriesID ?: 0)

        assertNotNull(tvSeriesEntities.data)
        assertNotNull(tvSeriesEntities.data?.id)
        assertNotNull(tvSeriesEntities.data?.name)
        assertNotNull(tvSeriesEntities.data?.originalLanguage)
        assertNotNull(tvSeriesEntities.data?.overview)
        assertNotNull(tvSeriesEntities.data?.popularity)
        assertNotNull(tvSeriesEntities.data?.posterPath)
        assertNotNull(tvSeriesEntities.data?.firstAirDate)
        assertNotNull(tvSeriesEntities.data?.voteAverage)
        assertNotNull(tvSeriesEntities.data?.voteCount)

        assertEquals(tvSeriesResponses[0].id, tvSeriesEntities.data?.id)
        assertEquals(tvSeriesResponses[0].name, tvSeriesEntities.data?.name)
        assertEquals(tvSeriesResponses[0].originalLanguage, tvSeriesEntities.data?.originalLanguage)
        assertEquals(tvSeriesResponses[0].overview, tvSeriesEntities.data?.overview)
        assertEquals(tvSeriesResponses[0].popularity, tvSeriesEntities.data?.popularity)
        assertEquals(tvSeriesResponses[0].posterPath, tvSeriesEntities.data?.posterPath)
        assertEquals(tvSeriesResponses[0].firstAirDate, tvSeriesEntities.data?.firstAirDate)
        assertEquals(tvSeriesResponses[0].voteAverage, tvSeriesEntities.data?.voteAverage)
        assertEquals(tvSeriesResponses[0].voteCount, tvSeriesEntities.data?.voteCount)
    }

    @Test
    fun getFavoriteMovies() {
        @Suppress("UNCHECKED_CAST")
        val dataSourceFactory = Mockito.mock(DataSource.Factory::class.java) as DataSource.Factory<Int, MovieEntity>
        `when`(local.getFavoriteMovies()).thenReturn(dataSourceFactory)
        movieCatalogueRepository.getFavoriteMovies()

        val movieEntities = Resource.success(PagedListUtil.mockPagedList(DataDummy.generateDummyMovies()))
        verify(local).getFavoriteMovies()

        assertNotNull(movieEntities.data)
        assertNotNull(movieEntities.data?.get(0)?.id)
        assertNotNull(movieEntities.data?.get(0)?.title)
        assertNotNull(movieEntities.data?.get(0)?.originalLanguage)
        assertNotNull(movieEntities.data?.get(0)?.overview)
        assertNotNull(movieEntities.data?.get(0)?.popularity)
        assertNotNull(movieEntities.data?.get(0)?.posterPath)
        assertNotNull(movieEntities.data?.get(0)?.releaseDate)
        assertNotNull(movieEntities.data?.get(0)?.voteAverage)
        assertNotNull(movieEntities.data?.get(0)?.voteCount)

        assertEquals(movieResponses.size.toLong(), movieEntities.data?.size?.toLong())
        assertEquals(movieResponses[0].id, movieEntities.data?.get(0)?.id)
        assertEquals(movieResponses[0].title, movieEntities.data?.get(0)?.title)
        assertEquals(movieResponses[0].originalLanguage, movieEntities.data?.get(0)?.originalLanguage)
        assertEquals(movieResponses[0].overview, movieEntities.data?.get(0)?.overview)
        assertEquals(movieResponses[0].popularity, movieEntities.data?.get(0)?.popularity)
        assertEquals(movieResponses[0].posterPath, movieEntities.data?.get(0)?.posterPath)
        assertEquals(movieResponses[0].releaseDate, movieEntities.data?.get(0)?.releaseDate)
        assertEquals(movieResponses[0].voteAverage, movieEntities.data?.get(0)?.voteAverage)
        assertEquals(movieResponses[0].voteCount, movieEntities.data?.get(0)?.voteCount)
    }

    @Test
    fun getFavoriteTVSeries() {
        @Suppress("UNCHECKED_CAST")
        val dataSourceFactory = Mockito.mock(DataSource.Factory::class.java) as DataSource.Factory<Int, TVSeriesEntity>
        `when`(local.getFavoriteTVSeries()).thenReturn(dataSourceFactory)
        movieCatalogueRepository.getFavoriteTVSeries()

        val tvSeriesEntities = Resource.success(PagedListUtil.mockPagedList(DataDummy.generateDummyTVSeries()))
        verify(local).getFavoriteTVSeries()

        assertNotNull(tvSeriesEntities.data)
        assertNotNull(tvSeriesEntities.data?.get(0)?.id)
        assertNotNull(tvSeriesEntities.data?.get(0)?.name)
        assertNotNull(tvSeriesEntities.data?.get(0)?.originalLanguage)
        assertNotNull(tvSeriesEntities.data?.get(0)?.overview)
        assertNotNull(tvSeriesEntities.data?.get(0)?.popularity)
        assertNotNull(tvSeriesEntities.data?.get(0)?.posterPath)
        assertNotNull(tvSeriesEntities.data?.get(0)?.firstAirDate)
        assertNotNull(tvSeriesEntities.data?.get(0)?.voteAverage)
        assertNotNull(tvSeriesEntities.data?.get(0)?.voteCount)

        assertEquals(tvSeriesResponses.size.toLong(), tvSeriesEntities.data?.size?.toLong())
        assertEquals(tvSeriesResponses[0].id, tvSeriesEntities.data?.get(0)?.id)
        assertEquals(tvSeriesResponses[0].name, tvSeriesEntities.data?.get(0)?.name)
        assertEquals(tvSeriesResponses[0].originalLanguage, tvSeriesEntities.data?.get(0)?.originalLanguage)
        assertEquals(tvSeriesResponses[0].overview, tvSeriesEntities.data?.get(0)?.overview)
        assertEquals(tvSeriesResponses[0].popularity, tvSeriesEntities.data?.get(0)?.popularity)
        assertEquals(tvSeriesResponses[0].posterPath, tvSeriesEntities.data?.get(0)?.posterPath)
        assertEquals(tvSeriesResponses[0].firstAirDate, tvSeriesEntities.data?.get(0)?.firstAirDate)
        assertEquals(tvSeriesResponses[0].voteAverage, tvSeriesEntities.data?.get(0)?.voteAverage)
        assertEquals(tvSeriesResponses[0].voteCount, tvSeriesEntities.data?.get(0)?.voteCount)
    }
}