package com.picois.myalbumapplication.ViewModel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.picois.myalbumapplication.AlbumEndPoint
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

    init {
        getListAlbum()
        albumLiveData.value = listAlbum
    }

    fun getListAlbum(){
        val request = ServiceBuilder.buildService(AlbumEndPoint::class.java)
        val callListAlbum = request.getListAlbum()
        callListAlbum.enqueue(object: Callback<Album> {
            override fun onFailure(call: Call<Album>, t: Throwable) {
                Toast.makeText(context, "fail :)", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<Album>, response: Response<Album>) {
                response?.body()?.let {
                    if(it.albumId != currentAlbumId) {
                        populateListAblum(Album(it.albumId, 0, "", "", ""))
                        currentAlbumId = it.albumId
                    }
                    populateListAblum(it)
                }
            }
        })
    }

    private fun populateListAblum(album: Album){
        listAlbum.add(album)
    }
}