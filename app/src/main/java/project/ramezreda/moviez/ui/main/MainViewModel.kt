package project.ramezreda.moviez.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import project.ramezreda.moviez.data.repository.DataRepository
import project.ramezreda.moviez.data.room.entities.Movie

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private var repository: DataRepository = DataRepository(application)

    val movies = MutableLiveData<List<Movie>>()
    var searchText = MutableLiveData<String>("")

    suspend fun searchMovies(title: String) : Boolean {

        val list: List<Movie>?

        if(title.isEmpty()) {
            list = repository.getAllMovies()
        } else {
            list = repository.searchMovies(title)
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
        }

        GlobalScope.launch(Dispatchers.Main) { movies.value = list }

        return true
    }
}