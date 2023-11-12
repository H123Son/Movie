package com.e1120.movie

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.e1120.movie.adapter.FavAdapter
import com.e1120.movie.architecture_component.FavViewModel
import com.e1120.movie.architecture_component.db.entity.MovieFavEntity
import com.e1120.movie.databinding.ActivityFavoriteBinding
import com.e1120.movie.databinding.ActivityMainBinding
import com.e1120.movie.model.Movie
import com.google.gson.Gson

class FavMovieActivity : AppCompatActivity(), IClick.OnClickMovie {
    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var favAdapter: FavAdapter
    private lateinit var favVM: FavViewModel

    @Volatile
    lateinit var listMovieFav: List<MovieFavEntity>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolBar)
        binding.toolBar.setNavigationOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        favAdapter = FavAdapter(this)
        binding.rvFav.apply {
            layoutManager =
                LinearLayoutManager(this@FavMovieActivity, LinearLayoutManager.VERTICAL, false)
            adapter = favAdapter
        }

        favVM = ViewModelProvider(this)[FavViewModel::class.java]
        favVM.getListMovie().observe(this) { listMovieFavObserve ->
            if (listMovieFavObserve != null) {
                listMovieFav = listMovieFavObserve
                favAdapter.update(listMovieFavObserve)
            }
        }
    }

    override fun click(position: Int) {
        val gson = Gson()
        val intent = Intent(this, MovieInfoActivity::class.java)
        val movieFavEntity = listMovieFav[position]
        val movie = Movie(
            vote_count = movieFavEntity.vote_count,
            vote_average = movieFavEntity.vote_average,
            id = movieFavEntity.movieId,
            title = movieFavEntity.title,
            poster_path = movieFavEntity.poster_path,
            original_language = movieFavEntity.original_language,
            original_title = movieFavEntity.original_title,
            genre_ids = arrayOf(3),
            overview = movieFavEntity.overview,
            release_date = movieFavEntity.release_date
        )
        val strMovie = gson.toJson(movie)
        intent.putExtra("movie", strMovie )
        ViewModelProvider(this)[FavViewModel::class.java].isValid(movie.id)
        startActivity(intent)
    }
}