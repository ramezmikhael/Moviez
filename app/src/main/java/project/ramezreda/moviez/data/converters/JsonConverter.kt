package project.ramezreda.moviez.data.converters

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import project.ramezreda.moviez.data.room.entities.Movie

object JsonConverter {

    fun convertFromJson(json: String) : List<Movie> {

        val moviesListType = object : TypeToken<List<Movie>>() {}.type

        return Gson().fromJson(json, moviesListType)
    }
}