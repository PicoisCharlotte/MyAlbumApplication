package com.picois.myalbumapplication.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.picois.myalbumapplication.Model.Album
import com.picois.myalbumapplication.R
import kotlinx.android.synthetic.main.item_album.view.*
import kotlinx.android.synthetic.main.item_album_header.view.*

class AlbumAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val TYPE_HEADER = 0
    private val TYPE_ITEM = 1

    private var listAlbum: MutableList<Album> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)

        when(viewType){
            TYPE_HEADER -> return HeaderViewHolder(layoutInflater.inflate(R.layout.item_album_header, parent, false))
            else -> return ItemViewHolder(layoutInflater.inflate(R.layout.item_album, parent, false))
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
        return if(listAlbum[position].id == 0) TYPE_HEADER else TYPE_ITEM
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val currentItem = holder.itemView
        val currentAlbum = listAlbum[position]

        if(holder is HeaderViewHolder){

            currentItem.tvAlbumNumber.text = "Album n°${currentAlbum.albumId}"

        } else if (holder is ItemViewHolder){

            currentItem.tvTitle.text = currentAlbum.title

        }

    }
}

private class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    var tvAlbumNumber: TextView

    init {
        tvAlbumNumber =
            view.findViewById<View>(R.id.tvAlbumNumber) as TextView
    }
}

private class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var tvTitle: TextView

    init {
        tvTitle = itemView.findViewById<View>(R.id.tvTitle) as TextView
    }
}