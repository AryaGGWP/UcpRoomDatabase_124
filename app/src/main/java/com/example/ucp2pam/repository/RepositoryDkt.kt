package com.example.ucp2pam.repository

import com.example.ucp2pam.data.entity.Dokter
import kotlinx.coroutines.flow.Flow

interface RepositoryDkt {
    suspend fun insertDkt(dokter: Dokter)
    fun getDkt(idDkt: Int): Flow<Dokter>
    fun getAllDkt(): Flow<List<Dokter>>
}