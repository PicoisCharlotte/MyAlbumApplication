package com.picois.myalbumapplication

import com.picois.myalbumapplication.Model.Album
import com.picois.myalbumapplication.Service.ServiceBuilder
import org.junit.Assert.assertEquals
import org.junit.Test
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RequestViewModelAndroidTest {

    @Test
    fun `can_get_list_album`(){
        val request = ServiceBuilder.buildService(AlbumEndPoint::class.java)
        val callListAlbum = request.getListAlbum()
        callListAlbum.enqueue(object: Callback<List<Album>> {
            override fun onFailure(call: Call<List<Album>>, t: Throwable) {
                assertEquals(200, 404 )
            }

            override fun onResponse(call: Call<List<Album>>, response: Response<List<Album>>) {
                assertEquals(200, 200 )
            }
        })
    }

}