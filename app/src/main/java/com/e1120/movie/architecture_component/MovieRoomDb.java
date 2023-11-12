package com.e1120.movie.architecture_component;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.e1120.movie.architecture_component.db.dao.MovieFavDao;
import com.e1120.movie.architecture_component.db.entity.MovieFavEntity;

@Database(entities = MovieFavEntity.class, version = 3)
abstract class MovieRoomDb extends RoomDatabase {
    public abstract MovieFavDao getDao();

    private static MovieRoomDb dbMovie;

    public static synchronized MovieRoomDb getInstance(Context context) {
        if (dbMovie == null) {
            dbMovie = Room.databaseBuilder(context.getApplicationContext(),
                            MovieRoomDb.class, "movie_map").fallbackToDestructiveMigration()
                    .addCallback(callback).build();
        }
        return dbMovie;
    }

    private static final RoomDatabase.Callback callback = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
        }
    };
}
