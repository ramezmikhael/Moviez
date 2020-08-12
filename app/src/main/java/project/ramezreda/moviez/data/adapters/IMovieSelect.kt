package project.ramezreda.moviez.data.adapters

import project.ramezreda.moviez.data.room.entities.Movie

interface IMovieSelect {
    fun onMovieSelected(movie: Movie)
}
