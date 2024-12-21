package com.example.ucp2pam.repository

import com.example.ucp2pam.data.entity.Jadwal
import kotlinx.coroutines.flow.Flow

interface RepositoryJdw {
    suspend fun insertJdw(jadwal: Jadwal)
    suspend fun updateJdw(jadwal: Jadwal)
    suspend fun deleteJdw(jadwal: Jadwal)
    fun getAllJdw(): Flow<List<Jadwal>>
    fun getJdw(idJdw: Int): Flow<Jadwal>
}