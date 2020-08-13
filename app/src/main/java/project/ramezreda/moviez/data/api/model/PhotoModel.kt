package project.ramezreda.moviez.data.api.model

import com.google.gson.annotations.SerializedName

data class PhotoModel(
    val id: String,
    val owner: String,
    val secret: String,
    val server: String,
    val farm: String,
    val title: String,
    @SerializedName("ispublic")
    val isPublic: String,
    @SerializedName("isfriend")
    val isFriend: String,
    @SerializedName("isfamily")
    val isFamily: String
)
