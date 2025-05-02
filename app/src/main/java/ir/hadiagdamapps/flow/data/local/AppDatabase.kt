package ir.hadiagdamapps.flow.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [MetaData::class, Playlist::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase(){


    abstract fun musicMetadataDao(): MetaDataDao

    abstract fun playListDao(): PlaylistDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).build().also {
                    INSTANCE = it
                }
            }
        }
    }

}

