package ir.hadiagdamapps.flow.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MetaDataDao {

    @Query("SELECT * from music_metadata where songId = :songId")
    suspend fun getMusicMetaData(songId: String): MetaData?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(metaData: MetaData)

    @Query("UPDATE music_metadata SET playCount = playCount + 1, lastPlayed = :timestamp WHERE songId = :songId")
    suspend fun incrementPlayCount(songId: String, timestamp: Long)

}