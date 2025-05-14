package ir.hadiagdamapps.flow.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PlaylistDao {

    @Query("UPDATE playlist SET isSelected = :selected WHERE playListId = :playlistId")
    suspend fun updateSelected(playlistId: Long, selected: Boolean)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(playlist: Playlist)

    @Query("SELECT * from playlist")
    fun getPlaylists(): Flow<List<Playlist>>

    @Query("DELETE from playlist where playListId = :playlistId")
    fun deletePlaylist(playlistId: Long)

    @Query("UPDATE playlist SET title = :title where playListId = :playlistId")
    fun updatePlaylistId(playlistId: Long, title: String)

    @Query("UPDATE playlist SET songs = :songs where playListId = :playlistId")
    fun updatePlaylistSongs(playlistId: Long, songs: String)

    @Query("SELECT * from playlist where playListId = :playlistId LIMIT 1")
    fun getPlaylist(playlistId: Long): Playlist

}