package com.example.ucp2pam.repository

import com.example.ucp2pam.data.dao.DokterDao
import com.example.ucp2pam.data.dao.JadwalDao
import com.example.ucp2pam.data.entity.Dokter
import com.example.ucp2pam.data.entity.Jadwal
import kotlinx.coroutines.flow.Flow

class LocalRepositoryApp(
    private val dokterDao: DokterDao,
    private val jadwalDao: JadwalDao
) : RepositoryApp{
    //dokter
    override suspend fun insertDokter(dokter: Dokter) {
        dokterDao.insertDokter(dokter)
    }

    override fun getAllDokter(): Flow<List<Dokter>> {
        return dokterDao.getAllDokter()
    }

    //jadwal
    override suspend fun insertJadwal(jadwal: Jadwal) {
        jadwalDao.insertJadwal(jadwal)
    }

    override suspend fun updateJadwal(jadwal: Jadwal) {
        jadwalDao.updateJadwal(jadwal)
    }

    override suspend fun deleteJadwal(jadwal: Jadwal) {
        jadwalDao.deleteJadwal(jadwal)
    }

    override fun getAllJadwal(): Flow<List<Jadwal>> {
        return jadwalDao.getAllJadwal()
    }

    override fun getJadwal(id: Int): Flow<Jadwal> {
        return jadwalDao.getJadwal(id)
    }
}