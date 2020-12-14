package com.picois.myalbumapplication.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.picois.myalbumapplication.Model.Album

@Dao
interface AlbumDao {
    @Query("SELECT * FROM album")
    fun getAlbums(): MutableList<Album>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(vararg albums: Album)
}