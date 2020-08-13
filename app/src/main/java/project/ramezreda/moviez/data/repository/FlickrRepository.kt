package project.ramezreda.moviez.data.repository

import io.reactivex.Single
import project.ramezreda.moviez.BuildConfig
import project.ramezreda.moviez.data.api.FlickrApi
import project.ramezreda.moviez.data.api.FlickrClient
import project.ramezreda.moviez.data.api.model.FlickrResponseModel

class FlickrRepository {
    private val apiInterface: FlickrApi = FlickrClient.getClient()?.create(FlickrApi::class.java)

    fun searchPhotos(searchText: String): Single<FlickrResponseModel?>? {
        return apiInterface.searchPhotos(
            "flickr.photos.search",
            BuildConfig.FLICKR_APIKEY,
            searchText, 10, "json", 1
        )
    }
}