package project.ramezreda.moviez.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import project.ramezreda.moviez.data.repository.DataRepository
import project.ramezreda.moviez.data.room.entities.Movie

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private var repository: DataRepository = DataRepository(application)

    val movies = MutableLiveData<List<Movie>>()

    suspend fun getAllMovies() : List<Movie>? {
        // Do heavy process in a background thread
        return repository.getAllMovies()
    }
}