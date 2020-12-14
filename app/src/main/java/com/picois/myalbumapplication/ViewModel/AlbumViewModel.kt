package com.picois.myalbumapplication.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.picois.myalbumapplication.AlbumEndPoint
import com.picois.myalbumapplication.Database.AppDatabase
import com.picois.myalbumapplication.Model.Album
import com.picois.myalbumapplication.Service.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AlbumViewModel(app: Application)  : AndroidViewModel(app) {

    var albumLiveData : MutableLiveData<MutableList<Album>> = MutableLiveData()
    var listAlbum: MutableList<Album> = mutableListOf()
    var currentAlbumId: Int = 0
    var context = app.baseContext

    val db = AppDatabase.getInstance(context)

    var result: Int = 0

    init {
        getListAlbum()
        result = 0
    }

    fun getListAlbum(){
        val request = ServiceBuilder.buildService(AlbumEndPoint::class.java)
        val callListAlbum = request.getListAlbum()
        callListAlbum.enqueue(object: Callback<List<Album>> {
            override fun onFailure(call: Call<List<Album>>, t: Throwable) {
                result = 404
                getAllAlbum()
            }

            override fun onResponse(call: Call<List<Album>>, response: Response<List<Album>>) {
                response?.body()?.let {
                    populateListAlbum(it)
                }
            }
        })
    }

    private fun populateListAlbum(albums: List<Album>){

        albums.forEach { album ->
            if(album.albumId != currentAlbumId){
                db?.getDao()?.insertAll(Album(album.albumId, 0, "", "", ""))
                currentAlbumId = album.albumId
            }
            db?.getDao()?.insertAll(album)
        }
        result = 200
        getAllAlbum()
    }

    fun getAllAlbum() {
        db?.getDao()?.getAlbums()?.let { listAlbum.addAll(it) }
        albumLiveData.value = listAlbum
    }
}