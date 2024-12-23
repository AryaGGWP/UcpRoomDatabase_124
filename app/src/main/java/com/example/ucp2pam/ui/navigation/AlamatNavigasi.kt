package com.example.ucp2pam.ui.navigation

interface AlamatNavigasi {
    val route: String
}

object DestinasiHome : AlamatNavigasi {
    override val route = "home"
}

object DestinasiTambahDokter : AlamatNavigasi {
    override val route = "tambah_dokter"
}

object DestinasiHomeJadwal : AlamatNavigasi {
    override val route = "home_jadwal"
}

object DestinasiAddJadwal : AlamatNavigasi {
    override val route = "add_jadwal"
}

object DestinasiDetailJadwal : AlamatNavigasi {
    override val route = "detail_jadwal"
    const val ID = "id"
    val routesWithArg = "$route/{$ID}"
}

object DestinasiUpdateJadwal : AlamatNavigasi {
    override val route = "update_jadwal"
    const val ID = "id"
    val routesWithArg = "$route/{$ID}"
}
