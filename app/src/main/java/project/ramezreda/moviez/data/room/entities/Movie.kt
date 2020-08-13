package project.ramezreda.moviez.data.room.entities

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "movies")
data class Movie(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 1,

    @ColumnInfo(name = "title")
    var title: String,

    var genres: List<String>,
    var cast: List<String>,

    var year: Int,
    var rating: Int
) : Parcelable