package com.rizalfahlepi.moviecatalogue.utils

import com.rizalfahlepi.moviecatalogue.api.TheMovieDBApi
import com.rizalfahlepi.moviecatalogue.data.source.local.entity.MovieEntity
import com.rizalfahlepi.moviecatalogue.data.source.local.entity.TVSeriesEntity
import com.rizalfahlepi.moviecatalogue.data.source.remote.response.MovieResponse
import com.rizalfahlepi.moviecatalogue.data.source.remote.response.TVSeriesResponse
import java.util.*

object DataDummy {

    fun generateDummyMovies(): ArrayList<MovieEntity> {
        val movies = ArrayList<MovieEntity>()
        movies.run {
            add(
                MovieEntity(
                    454626,
                    "Sonic the Hedgehog",
                    "2020-02-12",
                    318.742,
                    413,
                    TheMovieDBApi.getPosterMovie("/s8qRIwA0zDPbnRekeU0rDwWE7q7.jpg"),
                    "en",
                    7.0,
                    "Based on the global blockbuster video game franchise from Sega, Sonic the Hedgehog tells the story of the world’s speediest hedgehog as he embraces his new home on Earth. In this live-action adventure comedy, Sonic and his new best friend team up to defend the planet from the evil genius Dr. Robotnik and his plans for world domination."
                )
            )
        }

        return  movies
    }

    fun generateDummyTVSeries(): ArrayList<TVSeriesEntity> {
        val movies = ArrayList<TVSeriesEntity>()
        movies.run {
            add(
                TVSeriesEntity(
                    456,
                    "The Simpsons",
                    "1989-12-17",
                    248.215,
                    2419,
                    TheMovieDBApi.getPosterMovie("/qcr9bBY6MVeLzriKCmJOv1562uY.jpg"),
                    "en",
                    7.2,
                    "Set in Springfield, the average American town, the show focuses on the antics and everyday adventures of the Simpson family; Homer, Marge, Bart, Lisa and Maggie, as well as a virtual cast of thousands. Since the beginning, the series has been a pop culture icon, attracting hundreds of celebrities to guest star. The show has also made name for itself in its fearless satirical take on politics, media and American life in general."
                )
            )
        }

        return  movies
    }

    fun generateRemoteDummyMovies(): ArrayList<MovieResponse> {
        val movies = ArrayList<MovieResponse>()
        movies.run {
            add(
                MovieResponse(
                   454626,
                    "Sonic the Hedgehog",
                    "2020-02-12",
                    318.742,
                    413,
                    TheMovieDBApi.getPosterMovie("/s8qRIwA0zDPbnRekeU0rDwWE7q7.jpg"),
                    "en",
                    7.0,
                    "Based on the global blockbuster video game franchise from Sega, Sonic the Hedgehog tells the story of the world’s speediest hedgehog as he embraces his new home on Earth. In this live-action adventure comedy, Sonic and his new best friend team up to defend the planet from the evil genius Dr. Robotnik and his plans for world domination."
                )
            )
        }

        return  movies
    }

    fun generateRemoteDummyTVSeries(): ArrayList<TVSeriesResponse> {
        val movies = ArrayList<TVSeriesResponse>()
        movies.run {
            add(
                TVSeriesResponse(
                    456,
                    "The Simpsons",
                    "1989-12-17",
                    248.215,
                    2419,
                    TheMovieDBApi.getPosterMovie("/qcr9bBY6MVeLzriKCmJOv1562uY.jpg"),
                    "en",
                    7.2,
                    "Set in Springfield, the average American town, the show focuses on the antics and everyday adventures of the Simpson family; Homer, Marge, Bart, Lisa and Maggie, as well as a virtual cast of thousands. Since the beginning, the series has been a pop culture icon, attracting hundreds of celebrities to guest star. The show has also made name for itself in its fearless satirical take on politics, media and American life in general."
                )
            )
        }

        return  movies
    }
}