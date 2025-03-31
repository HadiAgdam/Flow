package ir.hadiagdamapps.flow.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [MetaData::class], version = 1, exportSchema = false)
abstract class MetaDataDatabase : RoomDatabase(){


    abstract fun musicMetadataDao(): MetaDataDao

    companion object {
        @Volatile
        private var INSTANCE: MetaDataDatabase? = null

        fun getDatabase(context: Context): MetaDataDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    MetaDataDatabase::class.java,
                    "app_database"
                ).build().also {
                    INSTANCE = it
                }
            }
        }
    }

}

}