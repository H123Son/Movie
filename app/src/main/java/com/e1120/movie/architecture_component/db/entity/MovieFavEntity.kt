package com.e1120.movie.architecture_component.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "t_fav")
data class MovieFavEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var vote_count: Int = 0,
    var vote_average: Float = 0f,
    var movieId: Int = 0,
    var title: String? = null,
    var poster_path: String? = null,
    var original_language: String? = null,
    var original_title: String? = null,
    var overview: String? = null,
    var release_date: String? = null
)