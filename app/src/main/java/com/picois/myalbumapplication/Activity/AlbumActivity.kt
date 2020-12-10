package com.picois.myalbumapplication.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.picois.myalbumapplication.Adapter.AlbumAdapter
import com.picois.myalbumapplication.Model.Album
import com.picois.myalbumapplication.R
import com.picois.myalbumapplication.ViewModel.AlbumViewModel

class AlbumActivity : AppCompatActivity() {

    private lateinit var rvAlbum : RecyclerView
    private lateinit var adapterAlbum : AlbumAdapter
    lateinit var albumViewModel: AlbumViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_album)

        rvAlbum = findViewById(R.id.rvAlbum)
        albumViewModel = ViewModelProviders.of(this)[AlbumViewModel::class.java]
        albumViewModel.albumLiveData.observe(this, albumListObserver)


    }

    private val albumListObserver: Observer<MutableList<Album>> =
        Observer{ albumList ->
            adapterAlbum = AlbumAdapter()
            adapterAlbum.setData(albumList)
            rvAlbum.layoutManager = LinearLayoutManager(baseContext)
            rvAlbum.adapter = adapterAlbum
        }
}
