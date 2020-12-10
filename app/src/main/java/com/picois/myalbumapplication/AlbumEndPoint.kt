package com.picois.myalbumapplication

import com.picois.myalbumapplication.Model.Album
import retrofit2.Call
import retrofit2.http.GET

interface AlbumEndPoint {
    @GET("img/shared/technical-test.json")
    fun getListAlbum(): Call<Album>
}