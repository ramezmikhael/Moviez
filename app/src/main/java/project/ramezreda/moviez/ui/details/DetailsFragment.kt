package project.ramezreda.moviez.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.layout_movie_info.view.*
import kotlinx.android.synthetic.main.movie_list_item.view.rating
import kotlinx.android.synthetic.main.movie_list_item.view.title
import project.ramezreda.moviez.R
import project.ramezreda.moviez.data.adapters.FlickrAdapter
import project.ramezreda.moviez.data.converters.ListTypeConverters
import project.ramezreda.moviez.data.room.entities.Movie
import project.ramezreda.moviez.databinding.DetailsFragmentBinding
import project.ramezreda.moviez.ui.MainActivity
import project.ramezreda.moviez.ui.base.BaseFragment

class DetailsFragment : BaseFragment() {

    companion object {
        fun newInstance() = DetailsFragment()
    }

    private lateinit var viewModel: DetailsViewModel
    private lateinit var binding: DetailsFragmentBinding

    private val adapter: FlickrAdapter by lazy {
        FlickrAdapter(viewModel.response.value?.photos)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.details_fragment, container, false)
        viewModel = ViewModelProvider(this).get(DetailsViewModel::class.java)

        binding.photosRecyclerView.layoutManager = GridLayoutManager(context, 2)
        binding.photosRecyclerView.adapter = adapter
        fillMovieInfo()

        viewModel.response.observe(viewLifecycleOwner, Observer {
            refreshRecyclerView()
        })

        return binding.root
    }

    private fun refreshRecyclerView() {
        adapter.photos = viewModel.response.value?.photos
        adapter.notifyDataSetChanged()

        if (viewModel.response.value?.stat.equals("ok")) {
            binding.message.visibility = View.GONE
        } else {
            binding.message.visibility = View.VISIBLE
        }

        binding.progressBar.visibility = View.GONE
    }

    private fun fillMovieInfo() {
        val converter = ListTypeConverters()

        val movie = arguments?.getParcelable<Movie>(MainActivity.EXTRA_MOVIE)
        binding.movieInfo.title.text = movie?.title
        binding.movieInfo.year.text = movie?.year.toString()
        binding.movieInfo.rating.text = movie?.rating.toString()
        binding.movieInfo.ratingBar.rating = movie?.rating?.toFloat()!!
        binding.movieInfo.cast.text = converter.listToString(movie?.cast)
        binding.movieInfo.genres.text = converter.listToString(movie?.genres)

        viewModel.searchFlickr(movie.title)

    }
}