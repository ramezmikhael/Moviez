package project.ramezreda.moviez.data

import android.content.Context
import android.util.Log

object Utils {
    fun readAssets(context: Context): String {
        Log.d("Utils", "Reading assets")

        return context.assets.open("movies.json").bufferedReader().use { it.readText() }
    }
}