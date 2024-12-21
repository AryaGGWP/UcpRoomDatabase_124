package com.example.ucp2pam.repository

import com.example.ucp2pam.data.dao.JadwalDao
import com.example.ucp2pam.data.entity.Jadwal
import kotlinx.coroutines.flow.Flow

class LocalRepositoryJdw(private val jadwalDao: JadwalDao) : RepositoryJdw {
    override suspend fun insertJdw(jadwal: Jadwal){
        jadwalDao.insertJadwal(jadwal)
    }
    override suspend fun updateJdw(jadwal: Jadwal){
        jadwalDao.updateJadwal(jadwal)
    }
    override suspend fun deleteJdw(jadwal: Jadwal){
        jadwalDao.deleteJadwal(jadwal)
    }
    override fun getJdw(idJadwal: Int): Flow<Jadwal> {
        return jadwalDao.getJadwal(idJadwal)
    }
    override fun getAllJdw(): Flow<List<Jadwal>> {
        return jadwalDao.getAllJadwal()
    }
}