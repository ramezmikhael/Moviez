package project.ramezreda.moviez.ui.details

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import project.ramezreda.moviez.data.api.model.FlickrResponseModel
import project.ramezreda.moviez.data.repository.FlickrRepository

class DetailsViewModel : ViewModel() {

    var response = MutableLiveData<FlickrResponseModel>()

    fun searchFlickr(title: String) {
        val call = FlickrRepository()
            .searchPhotos(title)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe({
                response.postValue(it)
            }, {
                Log.d("Error", it?.message!!)
            })
    }
}