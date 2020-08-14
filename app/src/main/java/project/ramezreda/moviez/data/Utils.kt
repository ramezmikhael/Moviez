package project.ramezreda.moviez.data

import android.content.Context
import android.util.Log
import project.ramezreda.moviez.data.api.model.PhotoModel

object Utils {
    fun readAssets(context: Context): String {
        Log.d("Utils", "Reading assets")

        return context.assets.open("movies.json").bufferedReader().use { it.readText() }
    }

    fun buildPhotoUrl(photo: PhotoModel): String {
        return "https://farm${photo.farm}.staticflickr.com/${photo.server}/${photo.id}_${photo.secret}.jpg"
    }
}