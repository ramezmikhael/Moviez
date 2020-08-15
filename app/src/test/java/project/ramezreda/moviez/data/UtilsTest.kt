package project.ramezreda.moviez.data

import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import project.ramezreda.moviez.data.api.model.PhotoModel

class UtilsTest {

    private lateinit var photo: PhotoModel

    @Before
    fun setup() {
        photo =
            PhotoModel("50222408746",
                "86741979@N08",
                "65535",
                "65535",
                "66",
                "Operation NANOOK",
                "1",
                "0",
                "0")
    }

    @Test
    fun buildPhotoUrl_isCorrect() {
        assertEquals(Utils.buildPhotoUrl(photo),
            "https://farm66.staticflickr.com/65535/50222408746_65535.jpg")
    }
}