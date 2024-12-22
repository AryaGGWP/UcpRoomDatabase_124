package com.example.ucp2pam.ui.navigation

interface AlamatNavigasi {
    val route: String
}

object DestinasiHome : AlamatNavigasi {
    override val route = "home"
}

object DestinasiDokter : AlamatNavigasi {
    override val route = "dokter"
}

object DestinasiTambahDokter : AlamatNavigasi {
    override val route = "tambah_dokter"
}

object DestinasiJadwal : AlamatNavigasi {
    override val route = "jadwal"
}

object DestinasiDetailJadwal : AlamatNavigasi {
    override val route = "detail_jadwal"
    const val ID = "idJadwal"
    val routesWithArg = "$route/{$ID}" // Contoh: detail_jadwal/1
}

object DestinasiUpdateJadwal : AlamatNavigasi {
    override val route = "update_jadwal"
    const val ID = "idJadwal"
    val routesWithArg = "$route/{$ID}"
}

object DestinasiTambahJadwal : AlamatNavigasi {
    override val route = "tambah_jadwal"
}
