package com.rizalfahlepi.moviecatalogue.ui.activity.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.rizalfahlepi.moviecatalogue.R
import com.rizalfahlepi.moviecatalogue.ui.activity.favorite.FavoriteActivity
import com.rizalfahlepi.moviecatalogue.ui.adapter.MovieViewPagerAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbarMain)

        val adapter = MovieViewPagerAdapter(this, supportFragmentManager, 0)
        viewPagerMovie.adapter = adapter
        tabLayoutMovie.setupWithViewPager(viewPagerMovie)
        supportActionBar?.elevation = 0f
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_favorite_list -> startActivity(Intent(this, FavoriteActivity::class.java))
        }

        return super.onOptionsItemSelected(item)
    }
}
