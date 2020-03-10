package com.rizalfahlepi.moviecatalogue.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rizalfahlepi.moviecatalogue.R
import com.rizalfahlepi.moviecatalogue.api.TheMovieDBApi
import com.rizalfahlepi.moviecatalogue.data.source.local.entity.TVSeriesEntity
import com.rizalfahlepi.moviecatalogue.ui.activity.detailmovie.DetailMovieActivity
import kotlinx.android.synthetic.main.item_movie.view.*
import java.text.SimpleDateFormat
import java.util.*

class TVSeriesPagedListAdapter internal constructor() : PagedListAdapter<TVSeriesEntity, TVSeriesPagedListAdapter.CourseViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<TVSeriesEntity>() {
            override fun areItemsTheSame(oldItem: TVSeriesEntity, newItem: TVSeriesEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: TVSeriesEntity, newItem: TVSeriesEntity): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return CourseViewHolder(view)
    }

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        val course = getItem(position) as TVSeriesEntity
        holder.bind(course)
    }

    class CourseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        private val simpleDateFormatView = SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault())

        fun bind(movie: TVSeriesEntity) {
            with(itemView){
                Glide.with(context).load(TheMovieDBApi.getPosterMovie(movie.posterPath ?: "")).into(imageViewPosterMovie)
                textViewTitleMovie.text = movie.name
                textViewDateMovie.text = try {
                    simpleDateFormatView.format(simpleDateFormat.parse(movie.firstAirDate ?: "") ?: Date())
                }catch (e: Exception){
                    e.printStackTrace()
                    movie.firstAirDate
                }
                textViewScoreMovie.text = movie.voteAverage.toString()
                textViewOverviewMovie.text = movie.overview

                itemView.setOnClickListener {
                    itemView.context.startActivity(
                        Intent(context, DetailMovieActivity::class.java)
                            .putExtra(DetailMovieActivity.EXTRA_TYPE, 1)
                            .putExtra(DetailMovieActivity.EXTRA_MOVIE_ID, movie.id))
                }
            }
        }
    }
}