package com.deezer.test.ui.albumdetails

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.deezer.test.R
import com.deezer.test.data.model.DataTrackList
import kotlinx.android.synthetic.main.track_item.view.*
import kotlinx.coroutines.runBlocking

class TracksListAdapter : RecyclerView.Adapter<TracksListAdapter.AlbumViewHolder>() {

    private var tracksList = mutableListOf<DataTrackList>()

    lateinit var callback: ((DataTrackList) -> Unit)

    fun setTrackClickListener(trackClickListener: ((DataTrackList) -> Unit)) {
        this.callback = trackClickListener
    }

    fun setTracksList(list: List<DataTrackList>) {
        runBlocking {
            with(tracksList) {
                clear()
                addAll(list)
            }
            notifyItemInserted(tracksList.size - 1)
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        return AlbumViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.track_item, parent, false
            )
        )
    }

    override fun getItemCount(): Int = tracksList.size

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        val albumdata = tracksList[position]

        holder.Bind(albumdata, callback)
    }


    inner class AlbumViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun Bind(data: DataTrackList, trackClickListener: (DataTrackList) -> Unit) {
            itemView.track_title.text = data.title
            itemView.track_duration.text =
                itemView.context.getString(R.string.track_time, data.duration.div(60))

            itemView.track_img.setOnClickListener { trackClickListener.invoke(data) }
        }
    }

}