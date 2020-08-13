package project.ramezreda.moviez.ui.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import project.ramezreda.moviez.data.repository.DataRepository
import project.ramezreda.moviez.data.room.entities.Movie

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private var repository: DataRepository = DataRepository(application)

    val movies = MutableLiveData<List<Movie>>()

    suspend fun searchMovies(title: String) : List<Movie>? {
        Log.d("text", title + "")

        if(title.isEmpty())
            return repository.getAllMovies()

        val list = repository.searchMovies(title)
        if (list != null) {
            val iterator: MutableIterator<Movie> = list.iterator()

            var i = 0
            var year = 0
            while (iterator.hasNext()) {
                val movie = iterator.next()

                if(year != movie.year) {
                    i = 0
                    year = movie.year
                } else if(i > 4 && year == movie.year) {
                    iterator.remove()
                }
                i++
            }
        }
        return list
    }
}