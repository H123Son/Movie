package com.e1120.movie.architecture_component.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.e1120.movie.architecture_component.db.entity.MovieFavEntity

@Dao
interface MovieFavDao {
    @Query("SELECT * FROM t_fav ORDER BY id DESC")
    fun getAll(): LiveData<List<MovieFavEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(movieFavEntity: MovieFavEntity)

    @Query("select count(*) from t_fav where id = :id")
    fun isValid(id: Int): Int

    @Query("delete from t_fav where id =:id")
    abstract fun removeMovie(id: Int)
}