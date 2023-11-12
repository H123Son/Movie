package com.e1120.movie

import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.GridLayoutManager
import com.e1120.movie.adapter.MovieAdapter
import com.e1120.movie.architecture_component.FavViewModel
import com.e1120.movie.databinding.ActivityMainBinding
import com.e1120.movie.model.Movie
import com.e1120.movie.retrofit.MovieVM
import com.google.gson.Gson

class MainActivity : AppCompatActivity(), SharedPreferences.OnSharedPreferenceChangeListener,
    IClick.OnClickMovie {
    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPreferences: SharedPreferences

    @Volatile
    lateinit var movieAdapter: MovieAdapter
    private lateinit var vm: MovieVM

    @Volatile
    var listMovie: ArrayList<Movie> = ArrayList()

    companion object {
        const val BASE_URL = "https://image.tmdb.org/t/p/original"
        const val BASE_MOVIE_URL = "https://api.themoviedb.org/"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).apply {
            setContentView(this.root)
        }
        vm = MovieVM(this)
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        sharedPreferences.registerOnSharedPreferenceChangeListener(this)
        setSupportActionBar(binding.toolBar.toolbar)
        val key = sharedPreferences.getString(
            resources.getString(R.string.sort_key),
            resources.getString(R.string.m_v_1)
        )
        renderUI(key)
    }


    override fun onSharedPreferenceChanged(p0: SharedPreferences?, key: String?) {
        renderUI(p0!!.getString(key, resources.getString(R.string.m_v_1)))
    }

    private fun renderUI(uri: String?) {
        val width = Resources.getSystem().displayMetrics.widthPixels
        val span = if (width <= 1080) 2 else 3
        movieAdapter = MovieAdapter(this, width / span - 20)
        binding.recyclerViewHomepage.apply {
            adapter = movieAdapter
            layoutManager = GridLayoutManager(this@MainActivity, span)
        }
        val API_KEY = resources.getString(R.string.api_key)
        val language = resources.getString(R.string.language)
        vm.getMovies(uri, API_KEY, language, 2)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_actionbar, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when (id) {
            R.id.favourites -> startActivity(Intent(this, FavMovieActivity::class.java))
            R.id.settings -> startActivity(Intent(this, PreferencesActivity::class.java))
        }

        return super.onOptionsItemSelected(item)
    }

    override fun click(position: Int) {
        val gson = Gson()
        val movie = gson.toJson(listMovie[position])
        val i = Intent(this, MovieInfoActivity::class.java).putExtra("movie", movie)
        ViewModelProvider(this)[FavViewModel::class.java].isValid(listMovie[position].id)
        startActivity(i)
    }

    override fun onDestroy() {
        super.onDestroy()
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }
}