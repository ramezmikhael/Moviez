package project.ramezreda.moviez.data.repository

import project.ramezreda.moviez.data.room.entities.Movie

interface Repository {
    suspend fun getAllMovies(): List<Movie>?
    suspend fun searchMovies(title: String): MutableList<Movie>?
}