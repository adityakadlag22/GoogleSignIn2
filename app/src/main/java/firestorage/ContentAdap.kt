package firestorage

import android.content.ContentValues.TAG
import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.VideoView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import cycle.gear.googlesignin2.R
import firestorage.models.modelcontent

class ContentAdap(private var items: List<modelcontent>, private val context: Context) :
    RecyclerView.Adapter<ContentAdap.ViewHolder>() {



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
        if (item.type == "image") {
            Picasso.get().load(item.downloadURI).into(holder.imageView)
            holder.imageView.visibility = View.VISIBLE
            holder.videoView.visibility = View.GONE
        } else if (item.type == "video") {
            Log.d(TAG, "onBindViewHolder: Video loading")
            holder.imageView.visibility = View.GONE
            holder.videoView.visibility = View.VISIBLE
            val uri = item.downloadURI
            val vidUri = Uri.parse(uri)
            holder.videoView.setVideoURI(vidUri)
            holder.videoView.requestFocus()
            holder.videoView.start()
        }
    }



    override fun getItemCount() = items.size
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.imageViewListItem)
        val videoView: VideoView = view.findViewById(R.id.videoViewListItem)

    }
}
