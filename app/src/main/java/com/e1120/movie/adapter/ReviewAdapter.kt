package com.e1120.movie.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.e1120.movie.R
import com.e1120.movie.databinding.ItemReviewMovieBinding
import com.e1120.movie.model.Review

class ReviewAdapter : RecyclerView.Adapter<ReviewAdapter.ReviewVH>() {
    private var reviews = ArrayList<Review>()

    fun updateReview(updateReviews: ArrayList<Review>) {
        this.reviews = updateReviews
        notifyItemRangeChanged(0, reviews.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewVH {
        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.item_review_movie, parent, false)
        return ReviewVH(ItemReviewMovieBinding.bind(v))
    }

    override fun getItemCount(): Int = reviews.size

    override fun onBindViewHolder(holder: ReviewVH, position: Int) {
        holder.bind(review = reviews[position])
    }

    inner class ReviewVH(private val binding: ItemReviewMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(review: Review) {
            binding.tvUser.text = review.author
            binding.tvMovieReview.text = review.content
        }
    }
}