package com.rizalfahlepi.moviecatalogue.ui.activity.detailmovie

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.rizalfahlepi.moviecatalogue.R
import com.rizalfahlepi.moviecatalogue.api.TheMovieDBApi
import com.rizalfahlepi.moviecatalogue.data.source.local.entity.MovieEntity
import com.rizalfahlepi.moviecatalogue.data.source.local.entity.TVSeriesEntity
import com.rizalfahlepi.moviecatalogue.viewmodel.ViewModelFactory
import com.rizalfahlepi.moviecatalogue.vo.Status
import kotlinx.android.synthetic.main.activity_detail_movie.*
import java.text.SimpleDateFormat
import java.util.*

class DetailMovieActivity : AppCompatActivity() {

    private var menuItem: Menu? = null
    private lateinit var viewModel: DetailMovieViewModel

    companion object {
        const val EXTRA_MOVIE_ID = "extra_movie_id"
        const val EXTRA_TYPE = "extra_type"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_movie)

        setSupportActionBar(toolbarDetail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        toolbarDetail.setNavigationOnClickListener { finish() }


        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[DetailMovieViewModel::class.java]

        if (intent.hasExtra(EXTRA_MOVIE_ID) && intent.hasExtra(EXTRA_TYPE)){

            viewModel.setSelectedData(intent.getIntExtra(EXTRA_MOVIE_ID, 0))

            when(intent.getIntExtra(EXTRA_TYPE, 0)){
                0 -> {
                    viewModel.movie.observe(this, Observer {
                        if (it != null) {
                            when (it.status) {
                                Status.LOADING -> showProgress(true)
                                Status.SUCCESS -> if (it.data != null) {
                                    showProgress(false)
                                    initData(intent.getIntExtra(EXTRA_TYPE, 0), it.data, null)
                                }
                                Status.ERROR -> {
                                    showProgress(false)
                                    Toast.makeText(applicationContext, getString(R.string.text_something_went_wrong), Toast.LENGTH_SHORT).show()
                                }
                            }

                        }
                    })
                }

                1 -> {
                    viewModel.tvSeries.observe(this, Observer {
                        if (it != null) {
                            when (it.status) {
                                Status.LOADING -> showProgress(true)
                                Status.SUCCESS -> if (it.data != null) {
                                    showProgress(false)
                                    initData(intent.getIntExtra(EXTRA_TYPE, 0), null, it.data)
                                }
                                Status.ERROR -> {
                                    showProgress(false)
                                    Toast.makeText(applicationContext, getString(R.string.text_something_went_wrong), Toast.LENGTH_SHORT).show()
                                }
                            }

                        }
                    })
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detail, menu)
        menuItem = menu
        when(intent.getIntExtra(EXTRA_TYPE, 0)){
            0 -> {
                viewModel.movie.observe(this, Observer {
                    if (it != null) {
                        when (it.status) {
                            Status.LOADING -> showProgress(true)
                            Status.SUCCESS -> if (it.data != null) {
                                showProgress(false)
                                setFavoriteState(it.data.favorite)
                            }
                            Status.ERROR -> {
                                showProgress(false)
                                Toast.makeText(applicationContext, getString(R.string.text_something_went_wrong), Toast.LENGTH_SHORT).show()
                            }
                        }

                    }
                })
            }

            1 -> {
                viewModel.tvSeries.observe(this, Observer {
                    if (it != null) {
                        when (it.status) {
                            Status.LOADING -> showProgress(true)
                            Status.SUCCESS -> if (it.data != null) {
                                showProgress(false)
                                setFavoriteState(it.data.favorite)
                            }
                            Status.ERROR -> {
                                showProgress(false)
                                Toast.makeText(applicationContext, getString(R.string.text_something_went_wrong), Toast.LENGTH_SHORT).show()
                            }
                        }

                    }
                })
            }
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_favorites) {
            when(intent.getIntExtra(EXTRA_TYPE, 0)){
                0 -> viewModel.setFavoriteMovie()
                1 -> viewModel.setFavoriteTVSeries()
            }
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initData(type: Int, movie: MovieEntity?, tvSeries: TVSeriesEntity?){
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val simpleDateFormatView = SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault())

        when(type){
            0 -> {
                Glide.with(this).load(TheMovieDBApi.getPosterMovie(movie?.posterPath ?: "")).into(imageViewPosterValue)
                textViewIDValue.text = movie?.id.toString()
                textViewVoteAverageValue.text = movie?.voteAverage.toString()
                textViewVoteCountValue.text = movie?.voteCount.toString()
                textViewLanguageValue.text = try {
                    Locale(movie?.originalLanguage ?: "").displayLanguage
                }catch (e: Exception){
                    e.printStackTrace()
                    movie?.originalLanguage?.toUpperCase(Locale.getDefault())
                }
                textViewPopularityValue.text = movie?.popularity.toString()
                textViewOverviewValue.text = movie?.overview

                textViewTitleValue.text = movie?.title
                textViewDateValue.text = try {
                    simpleDateFormatView.format(simpleDateFormat.parse(movie?.releaseDate ?: "") ?: Date())
                }catch (e: Exception){
                    e.printStackTrace()
                    movie?.releaseDate
                }
            }

            1 -> {
                Glide.with(this).load(TheMovieDBApi.getPosterMovie(tvSeries?.posterPath ?: "")).into(imageViewPosterValue)
                textViewIDValue.text = tvSeries?.id.toString()
                textViewVoteAverageValue.text = tvSeries?.voteAverage.toString()
                textViewVoteCountValue.text = tvSeries?.voteCount.toString()
                textViewLanguageValue.text = try {
                    Locale(tvSeries?.originalLanguage ?: "").displayLanguage
                }catch (e: Exception){
                    e.printStackTrace()
                    tvSeries?.originalLanguage?.toUpperCase(Locale.getDefault())
                }
                textViewPopularityValue.text = tvSeries?.popularity.toString()
                textViewOverviewValue.text = tvSeries?.overview

                textViewTitleValue.text = tvSeries?.name
                textViewDateValue.text = try {
                    simpleDateFormatView.format(simpleDateFormat.parse(tvSeries?.firstAirDate ?: "") ?: Date())
                }catch (e: Exception){
                    e.printStackTrace()
                    tvSeries?.firstAirDate
                }
            }
        }
    }

    private fun showProgress(isActive: Boolean){
        if (isActive){
            progressBarDetailMovie.visibility = View.VISIBLE
        } else {
            progressBarDetailMovie.visibility = View.GONE
        }
    }

    private fun setFavoriteState(state: Boolean) {
        if (menuItem != null){
            if (state) {
                menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_favorite_added)
            } else {
                menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_favorite_add)
            }
        }
    }
}
