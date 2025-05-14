package ir.hadiagdamapps.flow.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "playlist")
data class Playlist(
    @PrimaryKey(autoGenerate = true) val playListId: Long,
    val title: String,
    val songs: String = "",
    val isSelected: Boolean = false
)