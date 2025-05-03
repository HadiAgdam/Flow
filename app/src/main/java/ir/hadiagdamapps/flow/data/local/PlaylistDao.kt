package ir.hadiagdamapps.flow.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
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

}