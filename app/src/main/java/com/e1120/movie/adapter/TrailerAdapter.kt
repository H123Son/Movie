package com.e1120.movie.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.e1120.movie.MovieInfoActivity
import com.e1120.movie.R
import com.e1120.movie.databinding.ItemTrailerGridBinding
import com.e1120.movie.model.Trailer

class TrailerAdapter(context: Context) :
    RecyclerView.Adapter<TrailerAdapter.VideoViewHolder>() {
    private var context: Context
    private var trailers = mutableListOf<Trailer>()


    init {
        this.context = context
    }

    fun update(listMovies: ArrayList<Trailer>) {
        trailers = listMovies
        notifyItemRangeChanged(0, trailers.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.item_trailer_grid, parent, false)
        return VideoViewHolder(ItemTrailerGridBinding.bind(v))
    }

    override fun getItemCount(): Int =
        trailers.size

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        holder.bind(trailers[position])
    }

    inner class VideoViewHolder(private val binding: ItemTrailerGridBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        fun bind(trailer: Trailer) {
            binding.trailerName.text = trailer.name
            binding.movTrailer.setOnClickListener(this)

        }

        override fun onClick(v: View) {
            val movieInfo = context as MovieInfoActivity
            movieInfo.clickTrailer(adapterPosition)
        }


    }


}