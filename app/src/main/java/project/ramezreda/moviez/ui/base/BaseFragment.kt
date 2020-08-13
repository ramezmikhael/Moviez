package project.ramezreda.moviez.ui.base

import android.view.Gravity
import androidx.fragment.app.Fragment
import androidx.transition.Slide

open class BaseFragment: Fragment() {
    init {
        // Make the fragment transition a little smoother and appealing
        enterTransition = Slide(Gravity.END)
        exitTransition = Slide(Gravity.START)
    }
}