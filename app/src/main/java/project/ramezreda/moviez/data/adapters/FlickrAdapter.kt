package project.ramezreda.moviez.data.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import project.ramezreda.moviez.R
import project.ramezreda.moviez.data.Utils
import project.ramezreda.moviez.data.api.model.PhotoModel
import project.ramezreda.moviez.data.api.model.Photos

class FlickrAdapter(var photos: Photos?) : RecyclerView.Adapter<FlickrAdapter.FlickrViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlickrViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.picture_list_item, parent, false)
        return FlickrViewHolder(view)
    }

    override fun getItemCount() : Int {
        return if(photos != null) {
            photos?.photo?.size!!
        } else
            0
    }

    override fun onBindViewHolder(holder: FlickrViewHolder, position: Int) {
        holder.bind(photos?.photo?.get(position)!!)
    }

    inner class FlickrViewHolder(private val view : View) : RecyclerView.ViewHolder(view) {
        private val image : ImageView = view.findViewById(R.id.photo)

        fun bind(photo: PhotoModel) {
            Glide.with(image.context).load(photo.let {
                Utils.buildPhotoUrl(
                    it
                )
            }).into(image)
        }
    }
}