package com.example.ucp2pam.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Dokter")
data class Dokter(
    @PrimaryKey(autoGenerate = true)
    val idDokter: Int = 0,
    val nama: String,
    val spesialis: String,
    val klinik: String,
    val noHp: String,
    val jamKerja: String
)
