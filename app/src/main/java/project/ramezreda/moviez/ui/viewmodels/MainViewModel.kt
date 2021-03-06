package project.ramezreda.moviez.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import project.ramezreda.moviez.data.repository.Repository
import project.ramezreda.moviez.data.room.entities.Movie

class MainViewModel(private val repository: Repository) : ViewModel() {

    val movies = MutableLiveData<List<Movie>>()
    var searchText = MutableLiveData<String>("")
    var isLoading = MutableLiveData<Boolean>(false)

    suspend fun searchMovies(title: String) : Boolean {

        withContext(Dispatchers.Main) {
            isLoading.postValue(true)
        }
        val list: List<Movie>?

        if(title.isEmpty()) {
            // If the search text is empty I return all the movies available. Getting all the movies is
            // straight forward with a logic explained in the DataRepository class
            list = repository.getAllMovies()
        } else {
            // When there is a search term, I get the result from RoomDB and filter the results here
            // We are only interested in the first 5 movies from each category (i.e. year) so I remove
            // everything else
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

        withContext(Dispatchers.Main) {
            isLoading.postValue(false)
            movies.postValue(list)
        }
        return true
    }
}