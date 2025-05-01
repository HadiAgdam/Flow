package ir.hadiagdamapps.flow.data.model

data class Track(
    val id: String,
    val title: String,
    val artist: String,
    val uri: String,
    val albumArtUri: String?,
    val duration: Long,
    val dateAdded: Long,
    var playCount: Int
)
