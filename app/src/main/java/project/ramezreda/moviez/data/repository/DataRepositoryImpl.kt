package project.ramezreda.moviez.data.repository

import android.content.Context
import project.ramezreda.moviez.data.Utils
import project.ramezreda.moviez.data.converters.JsonConverter
import project.ramezreda.moviez.data.room.MoviesDatabase
import project.ramezreda.moviez.data.room.entities.Movie

class DataRepositoryImpl constructor(private val context: Context) : Repository {
    private val db: MoviesDatabase? = MoviesDatabase.getDatabase(context)

    override suspend fun getAllMovies(): List<Movie>? {
        var movies = db?.movieDao()?.getAllMovies()

        // If there is no cached data in room db, parse the JSON file from the app assets
        // then cache the movies in the database
        if (movies == null || movies.isEmpty()) {
            movies = JsonConverter.convertFromJson(Utils.readAssets(context))

            // Cache movies in the DB
            for(movie in movies) {
                db?.movieDao()?.insert(movie)
            }
        }
        return movies
    }

    override suspend fun searchMovies(title: String): MutableList<Movie>? {
        return db?.movieDao()?.searchMovies("%$title%")
    }
}
