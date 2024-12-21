package com.example.ucp2pam.repository

import com.example.ucp2pam.data.dao.DokterDao
import com.example.ucp2pam.data.entity.Dokter
import kotlinx.coroutines.flow.Flow

class LocalRepositoryDkt(private val dokterDao: DokterDao): RepositoryDkt {
    override suspend fun insertDkt(dokter: Dokter) {
            dokterDao.insertDokter(dokter)
        }

    override fun getAllDkt(): Flow<List<Dokter>>{
        return dokterDao.getAllDokterName()
    }

    override fun getDkt(dokterId: Int): Flow<Dokter>{
        return dokterDao.getDokter(dokterId)
    }
}