package project.ramezreda.moviez.data.converters

import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import project.ramezreda.moviez.data.room.entities.Movie

class JsonConverterTest {

    val input =
        "[ { \"title\": \"(500) Days of Summer\", \"year\": 2009, \"cast\": [ \"Joseph Gordon-Levitt\", \"Zooey Deschanel\" ], \"genres\": [ \"Romance\", \"Comedy\" ], \"rating\": 1 }, " +
                "{ \"title\": \"12 Rounds\", \"year\": 2009, \"cast\": [ \"John Cena\", \"Ashley Scott\", \"Steve Harris\", \"Aidan Gillen\", \"Brian J. White\", \"Taylor Cole\" ], \"genres\": [ \"Action\" ], \"rating\": 1 }]"

    lateinit var output: List<Movie>

    @Before
    fun setup() {
        output = listOf(
            Movie(
                id = 0,
                title = "(500) Days of Summer",
                year = 2009,
                cast = listOf("Joseph Gordon-Levitt", "Zooey Deschanel"),
                genres = listOf("Romance", "Comedy"),
                rating = 1
            ),
            Movie(
                id = 0,
                title = "12 Rounds",
                year = 2009,
                cast = listOf(
                    "John Cena",
                    "Ashley Scott",
                    "Steve Harris",
                    "Aidan Gillen",
                    "Brian J. White",
                    "Taylor Cole"
                ),
                genres = listOf("Action"),
                rating = 1
            )
        )
    }

    @Test
    fun convertFromJson_shouldReturnValidList() {
        val list = JsonConverter.convertFromJson(input)
        assertThat(output, `is`(list))
    }
}