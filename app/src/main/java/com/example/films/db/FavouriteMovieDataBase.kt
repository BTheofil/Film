package com.example.films.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.films.db.entity.FavouriteMovie

@Database(
    entities = [FavouriteMovie::class],
    version = 1,
    exportSchema = false)
abstract class FavouriteMovieDataBase: RoomDatabase() {

    abstract fun favouriteMovieDAO(): FavouriteDAO

    companion object {
        @Volatile private var instance: FavouriteMovieDataBase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context)= instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also { instance = it}
        }

        fun buildDatabase(context: Context) = Room.databaseBuilder(context,
            FavouriteMovieDataBase::class.java, "favourite_movies.db")
            .build()
    }
}