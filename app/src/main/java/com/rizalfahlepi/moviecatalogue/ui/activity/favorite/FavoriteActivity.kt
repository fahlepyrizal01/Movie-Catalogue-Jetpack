package com.rizalfahlepi.moviecatalogue.ui.activity.favorite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rizalfahlepi.moviecatalogue.R
import com.rizalfahlepi.moviecatalogue.ui.adapter.MovieViewPagerAdapter
import kotlinx.android.synthetic.main.activity_favorite.*

class FavoriteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        toolbarFavorite.setNavigationOnClickListener { finish() }

        val adapter = MovieViewPagerAdapter(this, supportFragmentManager, 1)
        viewPagerFavorite.adapter = adapter
        tabLayoutFavorite.setupWithViewPager(viewPagerFavorite)
        supportActionBar?.elevation = 0f
    }
}
