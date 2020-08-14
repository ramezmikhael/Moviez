package project.ramezreda.moviez

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.`is`
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import project.ramezreda.moviez.data.room.MoviesDatabase
import project.ramezreda.moviez.data.room.dao.MovieDao
import project.ramezreda.moviez.data.room.entities.Movie
import java.io.IOException


@RunWith(AndroidJUnit4::class)
class PersistenceTest {

    private lateinit var movieDao: MovieDao
    private lateinit var db: MoviesDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, MoviesDatabase::class.java
        ).build()
        movieDao = db.movieDao()!!
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun writeMovieAndReadInList() = runBlocking {
        val movie: Movie = createMovie(
            1,
            "Sully",
            5,
            2017,
            listOf("Tom Hanks", "Aaron Eckhart"),
            listOf("Biography", "Drama")
        )
        movieDao.insert(movie)
        val byTitle = movieDao.searchMovies("Sully")

        Assert.assertThat(byTitle[0].title, CoreMatchers.equalTo(movie.title))
        Assert.assertThat(byTitle[0].year, CoreMatchers.equalTo(movie.year))
        Assert.assertThat(byTitle[0].cast, `is`(movie.cast))
        Assert.assertThat(byTitle[0].genres, `is`(movie.genres))
    }

    private fun createMovie(
        id: Int,
        title: String,
        rating: Int,
        year: Int,
        cast: List<String>,
        genres: List<String>
    ): Movie {
        return Movie(
            id = id,
            title = title,
            rating = rating,
            year = year,
            cast = cast,
            genres = genres
        )
    }

}