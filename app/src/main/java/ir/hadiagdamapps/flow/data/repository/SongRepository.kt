package ir.hadiagdamapps.flow.data.repository

import android.content.Context
import android.util.Log
import ir.hadiagdamapps.flow.data.model.Track
import ir.hadiagdamapps.flow.data.source.SongLoader
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object SongRepository {
    private var songList: List<Track> = emptyList()

    fun initialize(context: Context) {
        CoroutineScope(Dispatchers.IO).launch {
            songList = SongLoader(context).loadSongs()
        }
    }

    fun getSongs(): List<Track> = songList.also { Log.e("songs found", it.size.toString()) }
}