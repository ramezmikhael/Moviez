package project.ramezreda.moviez.ui.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import project.ramezreda.moviez.R
import project.ramezreda.moviez.data.adapters.IMovieSelect
import project.ramezreda.moviez.data.adapters.MoviesAdapter
import project.ramezreda.moviez.data.room.entities.Movie

class MainFragment : Fragment(), IMovieSelect {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var progressBar : ProgressBar
    private lateinit var recyclerViewMovies: RecyclerView
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: MoviesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.main_fragment, container, false)

        progressBar = view.findViewById(R.id.progressBar)
        recyclerViewMovies = view.findViewById(R.id.moviesRecyclerView)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        viewModel.movies.observe(viewLifecycleOwner, Observer {
            Log.d("movies", it?.size.toString())
            adapter.movies = it
            adapter.notifyDataSetChanged()
        })

        adapter = MoviesAdapter(viewModel.movies.value, this)

        initRecyclerView()
        getAllMovies()
    }

    private fun getAllMovies() {
        progressBar.visibility = View.VISIBLE
        val job = GlobalScope.async {
            viewModel.getAllMovies()
        }

        GlobalScope.launch(Dispatchers.Main) {
            val result = job.await()
            viewModel.movies.value = result
            progressBar.visibility = View.GONE
        }
    }

    private fun initRecyclerView() {
        recyclerViewMovies.adapter = adapter
        recyclerViewMovies.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }

    override fun onMovieSelected(movie: Movie) {
//        photoSelect.let {  photoSelect.onPhotoSelected(photo) }
    }
}