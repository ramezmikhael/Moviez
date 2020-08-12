package project.ramezreda.moviez.data.repository

import android.content.Context
import project.ramezreda.moviez.data.Utils
import project.ramezreda.moviez.data.converters.JsonConverter
import project.ramezreda.moviez.data.room.MoviesDatabase
import project.ramezreda.moviez.data.room.entities.Movie

class DataRepository constructor(private val context: Context) {
    private val db: MoviesDatabase? = MoviesDatabase.getDatabase(context)

    suspend fun getAllMovies(): List<Movie>? {
        var movies = db?.movieDao()?.getAllMovies()

        // If there is no cached data in room db, parse the JSON file from the app assits
        // then cache the movies in the database
        if (movies == null || movies?.isEmpty()) {
            movies = JsonConverter.ConvertFromJson(Utils.readAssets(context))

            // Cache movies in the DB
            for(movie in movies) {
                db?.movieDao()?.insert(movie)
            }
        }
        return movies
    }
}
