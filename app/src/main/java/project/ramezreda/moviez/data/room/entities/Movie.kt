package project.ramezreda.moviez.data.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class Movie(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 1,

    @ColumnInfo(name = "title")
    var title: String,

    var year: Int,
    var genre: String,
    var cast: String,
    var rating: Int
)