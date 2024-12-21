package com.example.ucp2pam.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.ucp2pam.data.entity.Dokter
import kotlinx.coroutines.flow.Flow

@Dao
interface DokterDao {
    @Insert
    suspend fun insertDokter(dokter: Dokter)

    @Query ("SELECT * FROM Dokter ORDER BY nama ASC")
    fun getAllDokterName(): Flow<List<Dokter>>

    @Query ("SELECT * FROM Dokter WHERE id = :dokterId")
    fun getDokter(dokterId: Int) : Flow<Dokter?>
}