package com.example.ucp2pam.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.ucp2pam.data.entity.Jadwal
import kotlinx.coroutines.flow.Flow

@Dao
interface JadwalDao {
    @Insert
    suspend fun  insertJadwal(jadwal: Jadwal): Long

    @Query("SELECT * FROM Jadwal ORDER BY namaPasien ASC")
    fun getAllJadwal() : Flow<List<Jadwal>>

    @Query("SELECT * FROM Jadwal WHERE id = :jadwalId")
    fun getJadwal(jadwalId: Int) : Flow<Jadwal?>

    @Delete
    suspend fun deleteJadwal(jadwal: Jadwal): Int

    @Update
    suspend fun updateJadwal(jadwal: Jadwal): Int
}