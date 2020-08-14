package project.ramezreda.moviez.ui.main

import android.os.Bundle
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
import project.ramezreda.moviez.ui.base.BaseFragment

class MainFragment : BaseFragment(), OnMovieSelect, MaterialSearchBar.OnSearchActionListener {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val viewModel: MainViewModel by lazy { ViewModelProvider(this).get(MainViewModel::class.java) }
    private lateinit var binding: MainFragmentBinding
    private lateinit var adapter: MoviesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.main_fragment, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.searchBar.setOnSearchActionListener(this)

        adapter = MoviesAdapter(viewModel.movies.value, this)
        binding.moviesRecyclerView.adapter = adapter
        binding.moviesRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        viewModel.movies.observe(viewLifecycleOwner, Observer {
            adapter.movies = it
            adapter.notifyDataSetChanged()
        })
        binding.searchBar.text = viewModel.searchText.value

        searchMovies(binding.searchBar.text)
    }

    private fun searchMovies(text: String?) {
        binding.progressBar.visibility = View.VISIBLE

        val job = GlobalScope.async { viewModel.searchMovies(text.toString()) }
        GlobalScope.launch(Dispatchers.Main) {
            val jobFinished = job.await()
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun onMovieSelected(movie: Movie) {
        (activity as MainActivity).movieSelected(movie)
    }

    override fun onButtonClicked(buttonCode: Int) {
    }

    override fun onSearchStateChanged(enabled: Boolean) {
    }

    override fun onSearchConfirmed(text: CharSequence?) {
        viewModel.searchText.value = text.toString()
        searchMovies(text.toString())
    }
}