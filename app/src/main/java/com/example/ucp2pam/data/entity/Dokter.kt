package com.example.ucp2pam.data.entity

import androidx.room.Entity

@Entity(tableName = "Dokter")
data class Dokter(
    val id: String,
    val nama: String,
    val spesialis: String,
    val klinik: String,
    val noHp: String,
    val jamKerja: String
)
