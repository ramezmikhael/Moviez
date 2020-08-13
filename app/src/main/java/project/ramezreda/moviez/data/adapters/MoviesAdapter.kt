package project.ramezreda.moviez.data.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import project.ramezreda.moviez.R
import project.ramezreda.moviez.callbacks.OnMovieSelect
import project.ramezreda.moviez.data.converters.ListTypeConverters
import project.ramezreda.moviez.data.room.entities.Movie

class MoviesAdapter(var movies: List<Movie>?, val movieSelect: OnMovieSelect) :
    RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>() {

    val converter = ListTypeConverters()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.movie_list_item, parent, false)
        return MovieViewHolder(view)
    }

    override fun getItemCount(): Int {
        if(movies == null)
            return 0
        return movies?.size!!
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        movies?.get(position)?.let { holder.bind(it, position) }
        if(position == 0) {
        }
    }

    inner class MovieViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        private val title: TextView = view.findViewById(R.id.title)
        private val genre: TextView = view.findViewById(R.id.genre)
        private val rating: TextView = view.findViewById(R.id.rating)
        private val header: TextView = view.findViewById(R.id.headerYear)


        fun bind(movie: Movie, position: Int) {
            title.text = movie.title
            genre.text = converter.listToString(movie.genres)
            rating.text = movie.rating.toString()
            header.text = movie.year.toString()

            if(position == 0) {
                header.visibility = View.VISIBLE
            } else {
                if(movie.year == movies?.get(position - 1)?.year) {
                    header.visibility = View.GONE
                } else {
                    header.visibility = View.VISIBLE
                }
            }

            view.setOnClickListener {
                movieSelect.let { movieSelect.onMovieSelected(movie) }
            }
        }
    }
}