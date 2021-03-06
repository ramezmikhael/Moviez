package project.ramezreda.moviez.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mancj.materialsearchbar.MaterialSearchBar
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import project.ramezreda.moviez.R
import project.ramezreda.moviez.callbacks.OnMovieSelect
import project.ramezreda.moviez.data.adapters.MoviesAdapter
import project.ramezreda.moviez.data.repository.DataRepositoryImpl
import project.ramezreda.moviez.data.room.entities.Movie
import project.ramezreda.moviez.databinding.MainFragmentBinding
import project.ramezreda.moviez.ui.MainActivity
import project.ramezreda.moviez.ui.base.BaseFragment
import project.ramezreda.moviez.ui.viewmodels.MainViewModel
import project.ramezreda.moviez.ui.viewmodels.factories.MainViewModelFactory

class MainFragment : BaseFragment(), OnMovieSelect, MaterialSearchBar.OnSearchActionListener {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(
            this, MainViewModelFactory(
                DataRepositoryImpl(context!!)
            )
        ).get(
            MainViewModel::class.java
        )
    }
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

        setupRecyclerView()
        setupObservers()

        // ViewModel outlives the fragment, so whenever the fragment is destroyed
        // and recreated I get the last search term from the viewModel
        binding.searchBar.text = viewModel.searchText.value

        searchMovies(binding.searchBar.text)
    }

    private fun setupObservers() {
        viewModel.movies.observe(viewLifecycleOwner, Observer {
            adapter.movies = it
            adapter.notifyDataSetChanged()
        })

        viewModel.isLoading.observe(viewLifecycleOwner, Observer {
            if(it)
                progressBar.visibility = View.VISIBLE
            else
                progressBar.visibility = View.GONE
        })
    }

    private fun setupRecyclerView() {
        adapter = MoviesAdapter(viewModel.movies.value, this)
        binding.moviesRecyclerView.adapter = adapter
        binding.moviesRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }

    private fun searchMovies(text: String?) {
        // Coroutine job will be running asynchronously in a background thread
        GlobalScope.launch { viewModel.searchMovies(text.toString()) }
    }

    // A callback to the parent activity informing it that a movie was selected from the list
    // so that from there we can replace the fragment with the details fragment
    override fun onMovieSelected(movie: Movie) {
        (activity as MainActivity).movieSelected(movie)
    }

    override fun onButtonClicked(buttonCode: Int) {
    }

    override fun onSearchStateChanged(enabled: Boolean) {
    }

    override fun onSearchConfirmed(text: CharSequence?) {
        // Save the search term in the viewmodel so that it can live a configuration change
        viewModel.searchText.value = text.toString()
        searchMovies(text.toString())
    }
}