package com.picois.myalbumapplication.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.picois.myalbumapplication.Model.Album
import com.picois.myalbumapplication.R
import kotlinx.android.synthetic.main.item_album.view.*
import kotlinx.android.synthetic.main.item_album_header.view.*

class AlbumAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val TYPE_HEADER = 0
    private val TYPE_ITEM = 1

    private var listAlbum: MutableList<Album> = mutableListOf()

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        this.context = parent.context

        when (viewType) {
            TYPE_ITEM -> return ItemViewHolder(layoutInflater.inflate(R.layout.item_album, parent,false))
            else -> return HeaderViewHolder(layoutInflater.inflate(R.layout.item_album_header, parent,false))
        }
    }

    fun setData(data: MutableList<Album>){
        this.listAlbum = data
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return listAlbum.size
    }

    override fun getItemViewType(position: Int): Int {
        if (listAlbum[position].id == 0) {
            return TYPE_HEADER;
        }
        return TYPE_ITEM;
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val currentItem = holder.itemView
        val currentAlbum = listAlbum[position]

        if(holder is HeaderViewHolder){

            currentItem.tvAlbumNumber.text = "Album n°${currentAlbum.albumId}"

        } else if (holder is ItemViewHolder){

            currentItem.tvTitle.text = currentAlbum.title
            Glide.with(context).load(currentAlbum.thumbnailUrl + ".png").placeholder(R.drawable.placeholder_images).into(currentItem.imageThumbnail)

        }

    }
}

private class HeaderViewHolder(view: View) : ViewHolder(view) {
    var tvAlbumNumber: TextView

    init {
        tvAlbumNumber =
            view.findViewById<View>(R.id.tvAlbumNumber) as TextView
    }
}

private class ItemViewHolder(itemView: View) : ViewHolder(itemView) {
    var tvTitle: TextView
    var imageThumbnail: ImageView

    init {
        tvTitle = itemView.findViewById<View>(R.id.tvTitle) as TextView
        imageThumbnail = itemView.findViewById<View>(R.id.imageThumbnail) as ImageView
    }
}