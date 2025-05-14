package ir.hadiagdamapps.flow.data.repository

import ir.hadiagdamapps.flow.data.local.Playlist
import ir.hadiagdamapps.flow.data.local.PlaylistDao
import kotlinx.coroutines.flow.Flow

class PlaylistRepository(private val dao: PlaylistDao) {

    val playlists: Flow<List<Playlist>> = dao.getPlaylists()

    suspend fun create(playlist: Playlist) = dao.insert(playlist)

    fun delete(playlistId: Long) = dao.deletePlaylist(playlistId)

    fun updatePlaylistTitle(playlistId: Long, title: String) = dao.updatePlaylistId(playlistId, title)

    suspend fun updatePlaylistSongs(playlistId: Long, songs: String) = dao.updatePlaylistSongs(playlistId, songs)

    fun getPlaylist(playlistId: Long) = dao.getPlaylist(playlistId)

}