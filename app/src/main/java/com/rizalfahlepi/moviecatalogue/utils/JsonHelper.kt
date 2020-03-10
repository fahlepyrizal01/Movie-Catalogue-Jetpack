package com.rizalfahlepi.moviecatalogue.utils

import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.rizalfahlepi.moviecatalogue.api.TheMovieDBApi
import com.rizalfahlepi.moviecatalogue.data.source.remote.response.MovieResponse
import com.rizalfahlepi.moviecatalogue.data.source.remote.response.TVSeriesResponse
import cz.msebera.android.httpclient.Header
import org.json.JSONObject
import java.util.*

class JsonHelper {

    fun loadMovieList(listener: (ArrayList<MovieResponse>) -> Unit) {
        val listItems = ArrayList<MovieResponse>()

        val client = AsyncHttpClient()
        client.get(TheMovieDBApi.getListMovie(), object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<Header>, responseBody: ByteArray) {
                try {
                    val result = String(responseBody)
                    val responseObject = JSONObject(result)
                    val list = responseObject.getJSONArray("results")

                    for (i in 0 until list.length()) {
                        val movie = list.getJSONObject(i)
                        listItems.add(
                            MovieResponse(
                                movie.getInt("id"),
                                movie.getString("title"),
                                movie.getString("release_date"),
                                movie.getDouble("popularity"),
                                movie.getInt("vote_count"),
                                movie.getString("poster_path"),
                                movie.getString("original_language"),
                                movie.getDouble("vote_average"),
                                movie.getString("overview")
                            )
                        )
                    }

                    listener.invoke(listItems)
                } catch (e: Exception) {
                    listItems.add(MovieResponse())
                    listener.invoke(listItems)
                    e.printStackTrace()
                }
            }

            override fun onFailure(statusCode: Int, headers: Array<Header>?, responseBody: ByteArray?, error: Throwable?) {
                listItems.add(MovieResponse())

                listener.invoke(listItems)
            }
        })
    }

    fun loadTVSeriesList(listener: (ArrayList<TVSeriesResponse>) -> Unit) {
        val listItems = ArrayList<TVSeriesResponse>()

        val client = AsyncHttpClient()
        client.get(TheMovieDBApi.getListTVSeries(), object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<Header>, responseBody: ByteArray) {
                try {
                    val result = String(responseBody)
                    val responseObject = JSONObject(result)
                    val list = responseObject.getJSONArray("results")

                    for (i in 0 until list.length()) {
                        val tvSeries = list.getJSONObject(i)
                        listItems.add(
                            TVSeriesResponse(
                                tvSeries.getInt("id"),
                                tvSeries.getString("name"),
                                tvSeries.getString("first_air_date"),
                                tvSeries.getDouble("popularity"),
                                tvSeries.getInt("vote_count"),
                                tvSeries.getString("poster_path"),
                                tvSeries.getString("original_language"),
                                tvSeries.getDouble("vote_average"),
                                tvSeries.getString("overview")
                            )
                        )
                    }

                    listener.invoke(listItems)
                } catch (e: Exception) {
                    listItems.add(TVSeriesResponse())
                    listener.invoke(listItems)
                    e.printStackTrace()
                }
            }

            override fun onFailure(statusCode: Int, headers: Array<Header>?, responseBody: ByteArray?, error: Throwable?) {
                listItems.add(TVSeriesResponse())

                listener.invoke(listItems)
            }
        })
    }

    fun loadMovieDetail(movieID: Int, listener: (MovieResponse) -> Unit) {
        var movieResponse: MovieResponse?

        val client = AsyncHttpClient()
        client.get(TheMovieDBApi.getDetailMovie(movieID), object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<Header>, responseBody: ByteArray) {
                try {
                    val result = String(responseBody)
                    val responseObject = JSONObject(result)
                    movieResponse = MovieResponse(
                        responseObject.getInt("id"),
                        responseObject.getString("title"),
                        responseObject.getString("release_date"),
                        responseObject.getDouble("popularity"),
                        responseObject.getInt("vote_count"),
                        responseObject.getString("poster_path"),
                        responseObject.getString("original_language"),
                        responseObject.getDouble("vote_average"),
                        responseObject.getString("overview")
                    )

                    listener.invoke(movieResponse as MovieResponse)
                } catch (e: Exception) {
                    movieResponse = MovieResponse()
                    listener.invoke(movieResponse as MovieResponse)
                    e.printStackTrace()
                }

            }

            override fun onFailure(statusCode: Int, headers: Array<Header>?, responseBody: ByteArray?, error: Throwable?) {
                movieResponse = MovieResponse()

                listener.invoke(movieResponse as MovieResponse)
            }
        })
    }

    fun loadTVSeriesDetail(tvSeriesID: Int, listener: (TVSeriesResponse) -> Unit) {
        var tvSeriesResponse: TVSeriesResponse?

        val client = AsyncHttpClient()
        client.get(TheMovieDBApi.getDetailTVSeries(tvSeriesID), object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<Header>, responseBody: ByteArray) {
                try {
                    val result = String(responseBody)
                    val responseObject = JSONObject(result)
                    tvSeriesResponse = TVSeriesResponse(
                        responseObject.getInt("id"),
                        responseObject.getString("name"),
                        responseObject.getString("first_air_date"),
                        responseObject.getDouble("popularity"),
                        responseObject.getInt("vote_count"),
                        responseObject.getString("poster_path"),
                        responseObject.getString("original_language"),
                        responseObject.getDouble("vote_average"),
                        responseObject.getString("overview")
                    )

                    listener.invoke(tvSeriesResponse as TVSeriesResponse)
                } catch (e: Exception) {
                    tvSeriesResponse = TVSeriesResponse()
                    listener.invoke(tvSeriesResponse as TVSeriesResponse)
                    e.printStackTrace()
                }

            }

            override fun onFailure(statusCode: Int, headers: Array<Header>?, responseBody: ByteArray?, error: Throwable?) {
                tvSeriesResponse = TVSeriesResponse()

                listener.invoke(tvSeriesResponse as TVSeriesResponse)
            }
        })
    }
}