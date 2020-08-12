package project.ramezreda.moviez.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.appbar.CollapsingToolbarLayout
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import project.ramezreda.moviez.R
import project.ramezreda.moviez.ui.viewmodels.MainViewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
        findViewById<CollapsingToolbarLayout>(R.id.toolbar_layout).title = title

        viewModel.movies.observe(this, Observer {
            Log.d("movies", it?.size.toString())
        })
        getAllMovies()
    }

    private fun getAllMovies() {
        //TODO show loading indicator
        val job = GlobalScope.async {
            viewModel.getAllMovies()
        }

        GlobalScope.launch(Dispatchers.Main) {
            val result = job.await()
            viewModel.movies.value = result
            // TODO hide loading indicator
        }
    }

}