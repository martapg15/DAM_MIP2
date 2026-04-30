package dam.a51564.mip2.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import dam.a51564.mip2.R

/**
 * Adapter for the RecyclerView displaying the grid of dog images.
 */
class DogImageAdapter(
    private var imageUrls: List<String> = emptyList()
) : RecyclerView.Adapter<DogImageAdapter.DogImageViewHolder>() {

    fun updateImages(newUrls: List<String>) {
        imageUrls = newUrls
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogImageViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_dog_image, parent, false)
        return DogImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: DogImageViewHolder, position: Int) {
        holder.bind(imageUrls[position])
    }

    override fun getItemCount(): Int = imageUrls.size

    class DogImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.imageViewDogItem)

        fun bind(url: String) {
            Glide.with(itemView.context)
                .load(url)
                .transition(DrawableTransitionOptions.withCrossFade(300))
                .into(imageView)
        }
    }
}
