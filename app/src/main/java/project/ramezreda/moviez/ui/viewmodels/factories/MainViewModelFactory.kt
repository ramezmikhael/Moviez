package project.ramezreda.moviez.ui.viewmodels.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import project.ramezreda.moviez.data.repository.DataRepository
import project.ramezreda.moviez.ui.viewmodels.MainViewModel

class MainViewModelFactory(private val repository: DataRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(repository) as T
    }
}