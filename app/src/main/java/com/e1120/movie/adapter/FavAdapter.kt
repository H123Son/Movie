package com.e1120.movie.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.e1120.movie.FavMovieActivity
import com.e1120.movie.MainActivity
import com.e1120.movie.R
import com.e1120.movie.architecture_component.db.entity.MovieFavEntity
import com.e1120.movie.databinding.ItemFavMovieBinding
import com.e1120.movie.databinding.ItemMovieBinding
import com.e1120.movie.model.Movie
import kotlin.properties.Delegates

class FavAdapter(context: Context) : RecyclerView.Adapter<FavAdapter.MovieVH>() {
    private var movies = mutableListOf<Movie>()
    private var context: Context

    private var movieFav: List<MovieFavEntity>

    init {
        this.context = context
        movieFav = ArrayList()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieVH {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_fav_movie, parent, false)
        return MovieVH(ItemFavMovieBinding.bind(v))
    }

    fun update(movies: List<MovieFavEntity>) {
        this.movieFav = movies
        notifyItemRangeChanged(0, this.movieFav.size)
    }

    override fun getItemCount(): Int {
        return movieFav.size
    }

    override fun onBindViewHolder(holder: MovieVH, position: Int) {
        holder.bind(movieFav[position])
    }


    inner class MovieVH(private val binding: ItemFavMovieBinding) :
        RecyclerView.ViewHolder(binding.root),
        View.OnClickListener {
        init {
            binding.cvFavMovie.setOnClickListener(this)
        }


        fun bind(movieFavEntity: MovieFavEntity) {
            setImg(movieFavEntity.poster_path!!)
            binding.tvLike.text = movieFavEntity.vote_count.toString()
            binding.tvRatings.text = buildString {
                append("")
                append(movieFavEntity.vote_average)
            }
        }

        private fun setImg(url: String) {
            Glide.with(context)
                .load(MainActivity.BASE_URL + url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH)
                .error(R.drawable.ic_launcher)
                .centerCrop()
                .into(binding.ivMovie)
        }

        override fun onClick(view: View?) {
            val favActivity = context as FavMovieActivity
            favActivity.click(position = adapterPosition)
        }

    }
}