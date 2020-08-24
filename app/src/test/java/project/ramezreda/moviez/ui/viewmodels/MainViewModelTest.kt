package project.ramezreda.moviez.ui.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import project.ramezreda.moviez.data.repository.DataRepository
import project.ramezreda.moviez.data.room.entities.Movie
import project.ramezreda.moviez.ui.viewmodels.factories.MainViewModelFactory

class MainViewModelTest {

    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    lateinit var mainViewModel: MainViewModel
    lateinit var repository: DataRepository

    // observers
    lateinit var moviesObserver: Observer<List<Movie>>

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Before
    fun setup() {
        Dispatchers.setMain(mainThreadSurrogate)
        repository = mock()
        mainViewModel = spy(MainViewModelFactory(repository).create(MainViewModel::class.java))

        moviesObserver = mock()
        mainViewModel.movies.observeForever(moviesObserver)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    @Test
    fun searchMovies_shouldCall_Repository_GetAllMovies() {
        runBlocking {
            whenever(repository.searchMovies("")).thenReturn(mutableListOf())
            mainViewModel.searchMovies("")
            verify(repository).getAllMovies()
        }
    }

    @Test
    fun searchMovies_ShouldCall_Repository_SearchMovies() {
        runBlocking {
            whenever(repository.searchMovies("text")).thenReturn(mutableListOf())
            mainViewModel.searchMovies("text")
            verify(repository).searchMovies("text")
        }
    }

    @Test
    fun searchMovies_ShouldUpdate_MoviesList() {
        runBlocking {
            whenever(repository.getAllMovies()).thenReturn(mutableListOf())
            mainViewModel.searchMovies("")
            verify(moviesObserver).onChanged(any())
        }
    }
}