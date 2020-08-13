package project.ramezreda.moviez.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import project.ramezreda.moviez.data.room.entities.Movie

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movie: Movie?) : Long?

    @Query(
        """
        SELECT * FROM 
            (SELECT * FROM movies WHERE title LIKE :title ORDER BY rating DESC )
        ORDER BY year 
        """
    )
    suspend fun searchMovies(title: String): MutableList<Movie>

    @Query("SELECT * FROM movies")
    fun getAllMovies(): List<Movie>
}