package project.ramezreda.moviez.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mancj.materialsearchbar.MaterialSearchBar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import project.ramezreda.moviez.R
import project.ramezreda.moviez.callbacks.OnMovieSelect
import project.ramezreda.moviez.data.adapters.MoviesAdapter
import project.ramezreda.moviez.data.room.entities.Movie
import project.ramezreda.moviez.databinding.MainFragmentBinding
import project.ramezreda.moviez.ui.MainActivity

class MainFragment : Fragment(), OnMovieSelect, MaterialSearchBar.OnSearchActionListener {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var binding: MainFragmentBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: MoviesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.main_fragment, container, false)
        binding.searchBar.setOnSearchActionListener(this)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        viewModel.movies.observe(viewLifecycleOwner, Observer {
            adapter.movies = it
            adapter.notifyDataSetChanged()
        })

        adapter = MoviesAdapter(viewModel.movies.value, this)

        initRecyclerView()
        getAllMovies()
    }

    private fun getAllMovies() {
        binding.progressBar.visibility = View.VISIBLE
        val job = GlobalScope.async {
            viewModel.getAllMovies()
        }

        GlobalScope.launch(Dispatchers.Main) {
            viewModel.movies.value = job.await()
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun initRecyclerView() {
        binding.moviesRecyclerView.adapter = adapter
        binding.moviesRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }

    override fun onMovieSelected(movie: Movie) {
        (activity as MainActivity).movieSelected(movie)
    }

    override fun onButtonClicked(buttonCode: Int) {
    }

    override fun onSearchStateChanged(enabled: Boolean) {
    }

    override fun onSearchConfirmed(text: CharSequence?) {
        val job = GlobalScope.async { viewModel.searchMovies(text.toString()) }
        GlobalScope.launch(Dispatchers.Main) { viewModel.movies.value = job.await() }
    }
}