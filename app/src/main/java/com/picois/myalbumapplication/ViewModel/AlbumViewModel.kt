package com.picois.myalbumapplication.ViewModel

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.picois.myalbumapplication.AlbumEndPoint
import com.picois.myalbumapplication.Model.Album
import com.picois.myalbumapplication.Service.ServiceBuilder
import org.json.JSONArray
import org.json.JSONException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class AlbumViewModel(app: Application)  : AndroidViewModel(app) {

    var albumLiveData : MutableLiveData<MutableList<Album>> = MutableLiveData()
    var listAlbum: MutableList<Album> = mutableListOf()
    var currentAlbumId: Int = 0
    var context = app.baseContext

    init {
        //getListAlbum()
        readJson(context)
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




    fun getJsonDataFromAsset(context: Context, fileName: String): String? {
        val jsonString: String
        try {
            jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }
        return jsonString
    }

    fun readJson(context: Context){
        try {
            val obj = JSONArray(getJsonDataFromAsset(context, "album_data.json"))
            var albumId = 0
            for (i in 0 until obj.length()) {
                val albumObject = obj.getJSONObject(i)
                if(albumObject["albumId"] != albumId) {
                    albumId = albumObject["albumId"] as Int
                    listAlbum.add(Album(albumObject["albumId"] as Int, 0, "", "", ""))
                }
                listAlbum.add(Album(
                    albumObject["albumId"] as Int,
                    albumObject["id"] as Int,
                    albumObject["title"].toString(),
                    albumObject["url"].toString(),
                    albumObject["thumbnailUrl"].toString())
                )
            }
        }
        catch (e: JSONException) {
            e.printStackTrace()
        }
    }

}