package ir.hadiagdamapps.flow.navigation

import kotlinx.serialization.Serializable

@Serializable
object PlayingSongRoute


@Serializable
object SongsScreenRoute

@Serializable
data class PlaylistScreenRoute(
    val playlistId: Long
)
