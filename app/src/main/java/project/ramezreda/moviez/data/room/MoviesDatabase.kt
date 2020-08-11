package project.ramezreda.moviez.data.room

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import project.ramezreda.moviez.data.room.dao.MovieDao
import project.ramezreda.moviez.data.room.entities.Movie
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@Database(entities = [Movie::class], version = 1)
abstract class MoviesDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao?

    companion object {
        @Volatile
        private var instance: MoviesDatabase? = null
        private const val noOfThreads = 4
        val databaseWriteExecutor: ExecutorService = Executors.newFixedThreadPool(noOfThreads)

        private var dbCallback: Callback = object : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {

                // TODO populate database with data from asset
//                val json = readAssets()
                Log.d("JSON", "Callback")

            }
        }

        private fun readAssets() : String {
            Log.d("JSON", "Reading assets")

            val context = Application()
            return context.assets.open("movies.json").bufferedReader().use { it.readText() }
        }

        fun getDatabase(context: Context): MoviesDatabase? {
            if (instance == null) {
                synchronized(MoviesDatabase::class.java) {
                    if (instance == null) {
                        instance = Room.databaseBuilder(
                            context.applicationContext,
                            MoviesDatabase::class.java, "movies_database")
                            .addCallback(dbCallback)
                            .build()
                    }
                }
            }
            return instance
        }
    }
}