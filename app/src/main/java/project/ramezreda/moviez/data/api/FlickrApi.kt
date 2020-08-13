package project.ramezreda.moviez.data.api

import io.reactivex.Single
import project.ramezreda.moviez.data.api.model.FlickrResponseModel
import retrofit2.http.GET
import retrofit2.http.Query


interface FlickrApi {
    @GET("services/rest")
    fun searchPhotos(
        @Query("method") method: String?,
        @Query("api_key") apiKey: String?,
        @Query("text") text: String?,
        @Query("per_page") perPage: Int,
        @Query("format") format: String?,
        @Query("nojsoncallback") noJsonCallback: Int
    ): Single<FlickrResponseModel?>?
}