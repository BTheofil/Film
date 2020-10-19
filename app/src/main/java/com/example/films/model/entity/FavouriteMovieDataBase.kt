package com.example.films.model.entity

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [FavouriteMovie::class],
    version = 1,
    exportSchema = false)
abstract class FavouriteMovieDataBase: RoomDatabase() {

    abstract fun favouriteMovieDAO(): FavouriteDAO

    companion object {
        @Volatile
        private var INSTANCE: FavouriteMovieDataBase? = null

        fun getDataBase(context: Context): FavouriteMovieDataBase{
            val tempInstance = INSTANCE
            if(tempInstance != null) {
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FavouriteMovieDataBase::class.java,
                    "favourite_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}