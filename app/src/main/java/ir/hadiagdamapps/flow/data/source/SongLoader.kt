package ir.hadiagdamapps.flow.data.source

import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import ir.hadiagdamapps.flow.data.local.AppDatabase
import ir.hadiagdamapps.flow.data.model.Track
import ir.hadiagdamapps.flow.data.repository.MetaDataRepository

class SongLoader(private val context: Context) {

    private val repository =
        AppDatabase.getDatabase(context).musicMetadataDao().let { MetaDataRepository(it) }

    suspend fun loadSongs(): List<Track> {
        val trackList = mutableListOf<Track>()
        val contentResolver = context.contentResolver

        val projection = arrayOf(
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.DATA,
            MediaStore.Audio.Media.ALBUM_ID,
            MediaStore.Audio.Media.DURATION,
            MediaStore.Audio.Media.DATE_ADDED
        )

        val cursor = contentResolver.query(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            projection,
            null,
            null,
            null
        )

        cursor?.use {
            val idColumn = it.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)
            val titleColumn = it.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)
            val artistColumn = it.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)
            val uriColumn = it.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)
            val albumIdColumn = it.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID)
            val durationColumn = it.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)
            val dateAddedColumn = it.getColumnIndexOrThrow(MediaStore.Audio.Media.DATE_ADDED)

            while (it.moveToNext()) {
                val id = it.getString(idColumn)
                val title = it.getString(titleColumn)
                val artist = it.getString(artistColumn)
                val uri = it.getString(uriColumn)
                val albumId = it.getLong(albumIdColumn)
                val duration = it.getLong(durationColumn)
                val dateAdded = it.getLong(dateAddedColumn)

                val albumArtUri = getAlbumArtUri(albumId)

                trackList.add(
                    Track(
                        id,
                        title,
                        artist,
                        uri,
                        albumArtUri,
                        duration,
                        dateAdded,
                        repository.getPlayCount(id)
                    )
                )
            }
        }

        return trackList
    }

    private fun getAlbumArtUri(albumId: Long): String? {
        val albumUri = Uri.parse("content://media/external/audio/albumart")
        return Uri.withAppendedPath(albumUri, albumId.toString()).toString()
    }
}

