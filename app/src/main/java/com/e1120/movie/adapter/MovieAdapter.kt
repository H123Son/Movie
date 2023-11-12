package com.e1120.movie.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.e1120.movie.MainActivity
import com.e1120.movie.R
import com.e1120.movie.databinding.ItemMovieBinding
import com.e1120.movie.model.Movie
import kotlin.properties.Delegates

class MovieAdapter(context: Context, width: Int) : RecyclerView.Adapter<MovieAdapter.MovieVH>() {
    private var movies = mutableListOf<Movie>()
    private var _width by Delegates.notNull<Int>()
    private var context: Context

    init {
        _width = width
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieVH {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        v.layoutParams.width = _width
        return MovieVH(ItemMovieBinding.bind(v))
    }

    fun update(movies: ArrayList<Movie>) {
        this.movies = movies
        notifyItemRangeChanged(0, this.movies.size)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    override fun onBindViewHolder(holder: MovieVH, position: Int) {
        holder.bind(movies[position])
    }


    inner class MovieVH(private val binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root),
        View.OnClickListener {
        init {
            binding.cvMovie.setOnClickListener(this)
        }


        fun bind(movie: Movie) {
            binding.tvTitle.text = movie.title
            setImg(movie.poster_path!!)
            binding.tvLikes.text = movie.vote_count.toString()
            binding.tvRating.text = buildString {
                append("")
                append(movie.vote_average)
            }
        }

        private fun setImg(url: String) {
            Glide.with(context)
                .load(MainActivity.BASE_URL + url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH)
                .error(R.drawable.ic_launcher)
                .centerCrop()
                .into(binding.ivStar)
        }

        override fun onClick(view: View?) {
            val mainActivity = context as MainActivity
            mainActivity.click(position = adapterPosition)
        }

    }
}