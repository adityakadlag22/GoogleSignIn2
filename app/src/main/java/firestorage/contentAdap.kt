package firestorage

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.VideoView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import cycle.gear.googlesignin2.R
import firestorage.models.modelcontent

class contentAdap(private var items: List<modelcontent>, private val context: Context) :
    RecyclerView.Adapter<contentAdap.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.contentlistitem,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        when (item.type) {
            "video" -> {

                holder.imageView.visibility=View.GONE
            }
            "image" -> {
                Picasso.get().load(item.downloadURI).into(holder.imageView)
                holder.videoView.visibility=View.GONE
            }
        }

    }

    override fun getItemCount() = items.size
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView = view.findViewById<ImageView>(R.id.imageViewListItem)
        val videoView = view.findViewById<VideoView>(R.id.videoViewListItem)

    }
}
