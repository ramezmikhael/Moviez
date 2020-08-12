package project.ramezreda.moviez.data.converters

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import project.ramezreda.moviez.data.room.entities.Movie

object JsonConverter {

    fun ConvertFromJson(json: String) : List<Movie> {
        val gson = Gson()

        val moviesListType = object : TypeToken<List<Movie>>() {}.type

        return gson.fromJson(json, moviesListType)
    }
}