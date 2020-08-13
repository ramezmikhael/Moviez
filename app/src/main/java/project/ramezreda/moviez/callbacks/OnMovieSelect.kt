package project.ramezreda.moviez.callbacks

import project.ramezreda.moviez.data.room.entities.Movie

interface OnMovieSelect {
    fun onMovieSelected(movie: Movie)
}
