package ir.hadiagdamapps.flow.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "music_metadata")
data class MetaData (
    @PrimaryKey val songId: String,
    val playCount: Int = 0,
    val isLiked: Boolean = false,
    val lastPlayed: Long? = null
)