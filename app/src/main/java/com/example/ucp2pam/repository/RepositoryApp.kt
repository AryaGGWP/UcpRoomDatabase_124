package com.example.ucp2pam.repository

import com.example.ucp2pam.data.entity.Dokter
import com.example.ucp2pam.data.entity.Jadwal
import kotlinx.coroutines.flow.Flow

interface RepositoryApp {
    //dokter
    suspend fun insertDokter(dokter: Dokter)
    fun getAllDokter(): Flow<List<Dokter>>
    //jadwal
    suspend fun insertJadwal(jadwal: Jadwal)
    suspend fun updateJadwal(jadwal: Jadwal)
    suspend fun deleteJadwal(jadwal: Jadwal)
    fun getAllJadwal(): Flow<List<Jadwal>>
    fun getJadwal(idJdw: Int): Flow<Jadwal>
}