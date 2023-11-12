package com.e1120.movie.model

import com.google.gson.annotations.SerializedName


data class TrailerResponse(
    @SerializedName("results")
    val trailers: ArrayList<Trailer>
)
