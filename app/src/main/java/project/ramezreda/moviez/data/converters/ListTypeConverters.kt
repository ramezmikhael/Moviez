package project.ramezreda.moviez.data.converters

import android.util.Log
import androidx.room.TypeConverter

class ListTypeConverters {
    @TypeConverter
    fun listToString(value: List<String>?): String? {
        var csv = ""
        value?.map { csv += "$it," }
        return csv.removeSuffix(",")
    }

    @TypeConverter
    fun stringToList(value: String?): List<String>? {
        return value?.split(",")
    }
}