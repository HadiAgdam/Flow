package ir.hadiagdamapps.flow.data.repository

import ir.hadiagdamapps.flow.data.local.Playlist
import ir.hadiagdamapps.flow.data.local.PlaylistDao
import kotlinx.coroutines.flow.Flow

class PlaylistRepository(private val dao: PlaylistDao) {

    val playlists: Flow<List<Playlist>> = dao.getPlaylists()

    suspend fun add(playlist: Playlist) = dao.insert(playlist)

    suspend fun delete(playlistId: Long) = dao.deletePlaylist(playlistId)

    suspend fun updatePlaylistTitle(playlistId: Long, title: String) = dao.updatePlaylistId(playlistId, title)

    suspend fun updatePlaylistSongs(playlistId: Long, title: String) = dao.updatePlaylistSongs(playlistId, title)

}