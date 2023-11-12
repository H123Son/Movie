package com.e1120.movie.model

import com.google.gson.annotations.SerializedName

data class ResponseObject(
    @SerializedName("results")
    val movies : ArrayList<Movie>
)

