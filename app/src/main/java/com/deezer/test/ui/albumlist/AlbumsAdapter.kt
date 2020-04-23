package com.deezer.test.ui.albumlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.deezer.test.R
import com.deezer.test.data.model.Data
import com.deezer.test.utils.loadPicture
import kotlinx.android.synthetic.main.album_item.view.*
import kotlinx.coroutines.runBlocking

class AlbumsAdapter : RecyclerView.Adapter<AlbumsAdapter.AlbumViewHolder>() {

    private var albumList = mutableListOf<Data>()
    lateinit var callback: ((Data) -> Unit)

    inline fun setAlbumClickListener(noinline albumClickListener: ((Data) -> Unit)) {
        if (albumClickListener != null) {
            this.callback = albumClickListener
        }
    }

    fun setAlbum(list: List<Data>) {
        runBlocking {
            with(albumList) {
                clear()
                addAll(list)
            }
            notifyItemInserted(albumList.size - 1)
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        return AlbumViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.album_item, parent, false
            )
        )
    }

    override fun getItemCount(): Int = albumList.size

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        val albumdata = albumList[position]

        holder.Bind(albumdata, callback)
    }


    inner class AlbumViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun Bind(data: Data, albumClickListener: (Data) -> Unit) {

            itemView.album_title.text = data.title

            itemView.album_img.loadPicture(data.cover_medium)

            itemView.setOnClickListener { albumClickListener?.invoke(data) }

        }
    }

}