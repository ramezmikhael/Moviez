package project.ramezreda.moviez.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import project.ramezreda.moviez.data.converters.ListTypeConverters
import project.ramezreda.moviez.data.room.dao.MovieDao
import project.ramezreda.moviez.data.room.entities.Movie
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@Database(entities = [Movie::class], version = 1)
@TypeConverters(ListTypeConverters::class)
abstract class MoviesDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao?

    companion object {
        @Volatile
        private var instance: MoviesDatabase? = null
        private const val noOfThreads = 4
        val databaseWriteExecutor: ExecutorService = Executors.newFixedThreadPool(noOfThreads)


        fun getDatabase(context: Context): MoviesDatabase? {
            if (instance == null) {
                synchronized(MoviesDatabase::class.java) {
                    if (instance == null) {
                        instance = Room.databaseBuilder(
                            context.applicationContext,
                            MoviesDatabase::class.java, "movies_database"
                        ).build()
                    }
                }
            }
            return instance
        }
    }
}