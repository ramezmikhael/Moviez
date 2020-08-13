package project.ramezreda.moviez.data.api.model

import project.ramezreda.moviez.data.api.model.PhotoModel

data class Photos(
    val page: Int,
    val pages: String,
    val perPage: Int,
    val total: String,
    val photo: List<PhotoModel>
)
