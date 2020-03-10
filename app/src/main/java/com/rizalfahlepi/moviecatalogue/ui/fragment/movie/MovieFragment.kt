package com.rizalfahlepi.moviecatalogue.ui.fragment.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.rizalfahlepi.moviecatalogue.R
import com.rizalfahlepi.moviecatalogue.ui.adapter.MoviePagedListAdapter
import com.rizalfahlepi.moviecatalogue.ui.adapter.TVSeriesPagedListAdapter
import com.rizalfahlepi.moviecatalogue.viewmodel.ViewModelFactory
import com.rizalfahlepi.moviecatalogue.vo.Status
import kotlinx.android.synthetic.main.fragment_movie.*

class MovieFragment : Fragment() {

    private lateinit var viewModel: MovieViewModel
    private lateinit var movieAdapter: MoviePagedListAdapter
    private lateinit var tvSeriesAdapter: TVSeriesPagedListAdapter

    companion object {
        private const val ARG_POSITION = "position"
        fun newInstance(position: Int): MovieFragment {
            val fragment =
                MovieFragment()
            val bundle = Bundle()
            bundle.putInt(ARG_POSITION, position)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_movie, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null){
            if (arguments != null) {
                val factory = ViewModelFactory.getInstance(requireActivity())
                viewModel = ViewModelProvider(this, factory)[MovieViewModel::class.java]

                when(arguments?.getInt(ARG_POSITION)){
                    0 -> {
                        movieAdapter = MoviePagedListAdapter()
                        viewModel.getMovies().observe(this, Observer {
                            if (it != null){
                                when (it.status) {
                                    Status.LOADING -> showProgress(true)
                                    Status.SUCCESS -> {
                                        showProgress(false)
                                        showEmptyData(it.data?.isEmpty() ?: false)
                                        movieAdapter.submitList(it.data)
                                        movieAdapter.notifyDataSetChanged()
                                    }
                                    Status.ERROR -> {
                                        showProgress(false)
                                        Toast.makeText(context, getString(R.string.text_something_went_wrong), Toast.LENGTH_SHORT).show()
                                    }
                                }
                            }

                            with(recyclerViewMovies) {
                                layoutManager = LinearLayoutManager(context)
                                setHasFixedSize(true)
                                adapter = movieAdapter
                            }
                        })
                    }

                    1 -> {
                        tvSeriesAdapter = TVSeriesPagedListAdapter()
                        viewModel.getTVSeries().observe(this, Observer {
                            if (it != null){
                                when (it.status) {
                                    Status.LOADING -> showProgress(true)
                                    Status.SUCCESS -> {
                                        showProgress(false)
                                        showEmptyData(it.data?.isEmpty() ?: false)
                                        if (it.data?.isNotEmpty() == true){
                                            textViewEmpty.visibility = View.GONE
                                        }else{
                                            textViewEmpty.visibility = View.VISIBLE
                                        }
                                        tvSeriesAdapter.submitList(it.data)
                                        tvSeriesAdapter.notifyDataSetChanged()
                                    }
                                    Status.ERROR -> {
                                        showProgress(false)
                                        Toast.makeText(context, getString(R.string.text_something_went_wrong), Toast.LENGTH_SHORT).show()
                                    }
                                }
                            }

                            with(recyclerViewMovies) {
                                layoutManager = LinearLayoutManager(context)
                                setHasFixedSize(true)
                                adapter = tvSeriesAdapter
                            }
                        })
                    }

                    2 -> {
                        showProgress(true)
                        movieAdapter = MoviePagedListAdapter()
                        viewModel.getFavoriteMovies().observe(this, Observer {
                            showProgress(false)
                            if (it != null){
                                showEmptyData(it.isEmpty())
                                movieAdapter.submitList(it)
                                movieAdapter.notifyDataSetChanged()
                            }

                            with(recyclerViewMovies) {
                                layoutManager = LinearLayoutManager(context)
                                setHasFixedSize(true)
                                adapter = movieAdapter
                            }
                        })
                    }

                    3 -> {
                        showProgress(true)
                        tvSeriesAdapter = TVSeriesPagedListAdapter()
                        viewModel.getFavoriteTVSeries().observe(this, Observer {
                            showProgress(false)
                            showEmptyData(it.isEmpty())
                            if (it != null){
                                tvSeriesAdapter.submitList(it)
                                tvSeriesAdapter.notifyDataSetChanged()
                            }

                            with(recyclerViewMovies) {
                                layoutManager = LinearLayoutManager(context)
                                setHasFixedSize(true)
                                adapter = tvSeriesAdapter
                            }
                        })
                    }
                }
            }
        }
    }

    private fun showProgress(isActive: Boolean){
        if (isActive) {
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.GONE
        }
    }

    private fun showEmptyData(isEmpty: Boolean){
        if (isEmpty){
            textViewEmpty.visibility = View.VISIBLE
        }else{
            textViewEmpty.visibility = View.GONE
        }
    }
}
