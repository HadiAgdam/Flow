package ir.hadiagdamapps.flow.data.repository

import ir.hadiagdamapps.flow.data.local.MetaData
import ir.hadiagdamapps.flow.data.local.MetaDataDao

class MetaDataRepository(private val dao: MetaDataDao) {

    suspend fun getMusicMetadata(songId: String): MetaData? = dao.getMusicMetaData(songId)

    suspend fun getPlayCount(songId: String): Int = dao.getMusicMetaData(songId)?.playCount ?: 0.also {
        dao.insertOrUpdate(MetaData(songId = songId))
    }

    suspend fun incrementPlayCount(songId: String) {
        dao.incrementPlayCount(songId, System.currentTimeMillis()) // اوسدون ویرما
    }

}