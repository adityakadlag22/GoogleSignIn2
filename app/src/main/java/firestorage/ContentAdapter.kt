package firestorage

import android.content.ContentValues.TAG
import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.MediaController
import android.widget.ProgressBar
import android.widget.VideoView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import cycle.gear.googlesignin2.R
import firestorage.models.modelcontent

class ContentAdapter(private var items: List<modelcontent>, private val context: Context) :
    RecyclerView.Adapter<ContentAdapter.ViewHolder>() {


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
            holder.progressbar.visibility=View.GONE
        } else if (item.type == "video") {
            Log.d(TAG, "onBindViewHolder: Video loading")
            holder.imageView.visibility = View.GONE
            holder.videoView.visibility = View.VISIBLE
            holder.progressbar.visibility=View.GONE
            val uri = item.downloadURI
            // val vidUri = Uri.parse(uri)

            holder.videoView.setVideoURI(Uri.parse(uri))
            holder.videoView.requestFocus()
            holder.videoView.start()
            val mControl = MediaController(context)
            holder.videoView.setMediaController(mControl)
            mControl.setAnchorView(holder.videoView)

        }
    }


    override fun getItemCount() = items.size
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.imageViewListItem)
        val videoView: VideoView = view.findViewById(R.id.videoViewListItem)
        val progressbar:ProgressBar=view.findViewById(R.id.listItemProgress)

    }
}
