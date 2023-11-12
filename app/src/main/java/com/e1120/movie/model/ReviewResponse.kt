package com.e1120.movie.model

import com.google.gson.annotations.SerializedName

data class ReviewResponse(
    @SerializedName("results")
    val reviews: ArrayList<Review>
)
