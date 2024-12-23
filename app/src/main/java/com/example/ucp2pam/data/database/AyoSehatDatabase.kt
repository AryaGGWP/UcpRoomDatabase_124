package com.example.ucp2pam.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.ucp2pam.data.dao.DokterDao
import com.example.ucp2pam.data.dao.JadwalDao
import com.example.ucp2pam.data.entity.Dokter
import com.example.ucp2pam.data.entity.Jadwal

@Database(entities = [Dokter::class, Jadwal::class], version = 1, exportSchema = false)
abstract class AyoSehatDatabase : RoomDatabase() {
    abstract fun dokterDao(): DokterDao
    abstract fun jadwalDao(): JadwalDao

    companion object {
        @Volatile
        private var Instance: AyoSehatDatabase? = null

        fun getDatabase(context: Context): AyoSehatDatabase {
            return (Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    AyoSehatDatabase::class.java,
                    "AyoSehat"
                )
                    .build().also { Instance = it }
            })
        }
    }
}

