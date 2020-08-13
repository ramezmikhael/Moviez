package project.ramezreda.moviez.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.Toolbar
import androidx.transition.Slide
import project.ramezreda.moviez.R
import project.ramezreda.moviez.data.room.entities.Movie
import project.ramezreda.moviez.ui.details.DetailsFragment
import project.ramezreda.moviez.ui.main.MainFragment

class MainActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_MOVIE = "extra_movie"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }
    }

    fun movieSelected(movie: Movie) {
        val fragment = DetailsFragment.newInstance()
        val bundle = Bundle()

        bundle.putParcelable(EXTRA_MOVIE, movie)
        fragment.arguments = bundle

        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .addToBackStack(null)
            .commit()
    }
}