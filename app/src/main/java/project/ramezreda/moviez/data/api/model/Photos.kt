package project.ramezreda.moviez.data.api.model

data class Photos(
    val page: Int,
    val pages: String,
    val perPage: Int,
    val total: String,
    val photo: List<PhotoModel>
)
