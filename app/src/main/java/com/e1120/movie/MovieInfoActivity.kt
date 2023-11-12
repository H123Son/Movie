package com.e1120.movie

import android.content.Intent
import android.content.res.Resources
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.e1120.movie.adapter.RecommendationAdapter
import com.e1120.movie.adapter.ReviewAdapter
import com.e1120.movie.adapter.TrailerAdapter
import com.e1120.movie.architecture_component.FavViewModel
import com.e1120.movie.architecture_component.db.entity.MovieFavEntity
import com.e1120.movie.databinding.ActivityMovieInfoBinding
import com.e1120.movie.model.Movie
import com.e1120.movie.model.Review
import com.e1120.movie.model.Trailer
import com.e1120.movie.retrofit.MovieVM
import com.google.gson.Gson
import java.util.regex.Pattern

class MovieInfoActivity : AppCompatActivity(), IClick.OnClickMovie, IClick.OnClickTrailer {
    private var shortString: String? = null
    private lateinit var movie: Movie
    private var shortStringEnabled = true

    @Volatile
    lateinit var trailerAdapter: TrailerAdapter

    @Volatile
    lateinit var reviewAdapter: ReviewAdapter

    @Volatile
    lateinit var movieAdapter: RecommendationAdapter

    @Volatile
    lateinit var movies: ArrayList<Movie>

    @Volatile
    lateinit var trailerMovies: ArrayList<Trailer>
    lateinit var reviews: ArrayList<Review>

    private lateinit var binding: ActivityMovieInfoBinding

    private lateinit var movieVM: MovieVM
    private lateinit var favViewModel: FavViewModel


    companion object {
        @Volatile
        var isFavourite = true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        trailerAdapter = TrailerAdapter(this)
        reviewAdapter = ReviewAdapter()
        binding.rvMovieReview.apply {
            adapter = reviewAdapter
            layoutManager =
                LinearLayoutManager(this@MovieInfoActivity, LinearLayoutManager.VERTICAL, false)
        }

        binding.rvMovieTrailers.apply {
            adapter = trailerAdapter
            layoutManager =
                LinearLayoutManager(this@MovieInfoActivity, LinearLayoutManager.VERTICAL, false)
        }

        val width = Resources.getSystem().displayMetrics.widthPixels
        val span = if (width <= 1080) 2 else 3
        movieAdapter = RecommendationAdapter(this, width / span + 70)
        binding.rvMovieRecommendations.apply {
            adapter = movieAdapter
            layoutManager =
                LinearLayoutManager(this@MovieInfoActivity, LinearLayoutManager.HORIZONTAL, false)
        }

        movieVM = MovieVM(this)
        favViewModel = ViewModelProvider(this)[FavViewModel::class.java]

        val gson = Gson()
        val bundle = intent.extras
        if (bundle != null) {
            movie = gson.fromJson(bundle.getString("movie"), Movie::class.java)
            binding.tvMovieLikes.text = buildString {
                append(movie.vote_count)
            }

            binding.tvMovieRatings.text = buildString {
                append(movie.vote_average)
            }
            shortString = getShortDescription(movie.overview)
            if (shortString.equals(movie.overview)) {
                binding.tvReadMore.visibility = View.GONE
            }
            binding.apply {
                tvMovieDes.text = shortString
                tvMovieTitle.text = movie.title
            }
            Glide.with(this).load(MainActivity.BASE_URL + movie.poster_path)
                .into(binding.movieImg)

            movieVM.getTrailers(
                movie.id,
                resources.getString(R.string.api_key),
                resources.getString(R.string.language)
            )

            movieVM.getReviews(
                movie.id, resources.getString(R.string.api_key),
                resources.getString(R.string.language)
            )

            movieVM.getSimilar(
                movie.id, resources.getString(R.string.api_key),
                resources.getString(R.string.language)
            )
            if (!isFavourite) binding.ivFav.setImageResource(R.drawable.love);
            else binding.ivFav.setImageResource(R.drawable.ic_favorite_black_24dp);
        } else {
            Toast.makeText(this, "Something went wrong..", Toast.LENGTH_SHORT).show();
        }

        binding.tvReadMore.setOnClickListener {
            if (shortStringEnabled) {
                binding.apply {
                    tvMovieDes.text = movie.overview
                    tvReadMore.text = getString(R.string.txt_show_less)
                }
            } else {
                binding.apply {
                    tvMovieDes.text = shortString
                    tvReadMore.text = getString(R.string.txt_read_more)
                }
            }
            shortStringEnabled = !shortStringEnabled
        }

        favViewModel = ViewModelProvider(this)[FavViewModel::class.java]
        binding.ivFav.setOnClickListener {
            if (isFavourite) {
                favViewModel.add(
                    MovieFavEntity(0,
                        movie.vote_count,
                        movie.vote_average,
                        movie.id,
                        movie.title,
                        movie.poster_path!!,
                        movie.original_language!!,
                        movie.original_title!!,
                        movie.overview!!,
                        movie.release_date
                    )
                )
                binding.ivFav.setImageResource(R.drawable.love)
            } else {
                favViewModel.remove(movie.id)
                binding.ivFav.setImageResource(R.drawable.ic_favorite_black_24dp)
            }
            isFavourite = !isFavourite
        }
    }


    private fun getShortDescription(des: String?): String? {
        val matcher = Pattern.compile(".*?\\.").matcher(des!!)
        return if (matcher.find()) {
            matcher.group(0)
        } else {
            ""
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        isFavourite = true
        finish()
    }

    override fun click(position: Int) {
        val gson = Gson()
        val i = Intent(this, MovieInfoActivity::class.java)
        i.putExtra("movie", gson.toJson(movies[position]))
        startActivity(i)
    }

    override fun clickTrailer(position: Int) {
        val youtube =
            Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:${trailerMovies[position].key}"))
        startActivity(youtube)
    }
}