package project.ramezreda.moviez.data.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import project.ramezreda.moviez.R
import project.ramezreda.moviez.data.room.entities.Movie

class MoviesAdapter(var movies: List<Movie>?, val movieSelect: IMovieSelect) :
    RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>() {

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
        movies?.get(position)?.let { holder.bind(it) }
    }

    inner class MovieViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        private val title: TextView = view.findViewById(R.id.title)
        private val year: TextView = view.findViewById(R.id.year)
        private val rating: TextView = view.findViewById(R.id.rating)


        fun bind(movie: Movie) {
            title.text = movie.title
            year.text = movie.year.toString()
            rating.text = movie.rating.toString()

            view.setOnClickListener {
                movieSelect.let { movieSelect.onMovieSelected(movie) }
            }
        }
    }
}