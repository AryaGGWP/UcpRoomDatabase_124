package com.example.ucp2pam.ui.viewmodel.jadwal

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp2pam.data.entity.Dokter
import com.example.ucp2pam.data.entity.Jadwal
import com.example.ucp2pam.repository.RepositoryApp
import kotlinx.coroutines.launch

class AddJadwalViewModel(private val repositoryApp: RepositoryApp): ViewModel() {
    var uiState by mutableStateOf(JadwalUIState())
    init {
        loadDokterList()
    }
    private fun loadDokterList() {
        viewModelScope.launch {
            repositoryApp.getAllDokter()
                .collect { dokter ->
                    uiState = uiState.copy(dokterList = dokter)
                }
        }
    }

    fun updateState(JadwalEvent: JadwalEvent) {
        uiState = uiState.copy(
            jadwalEvent = JadwalEvent,
        )
    }
    private fun validateFields(): Boolean {
        val event = uiState.jadwalEvent
        val errorState = FormErrorStateJadwal(
            namaPasien = if (event.namaPasien.isNotEmpty()) null else "Nama Pasien tidak boleh kosong",
            namaDokter = if (event.namaDokter.isNotEmpty()) null else "Nama Dokter tidak boleh kosong",
            noHp = if (event.noHp.isNotEmpty()) null else "Nomor Hp tidak boleh kosong",
            tanggalKonsultasi = if (event.tanggalKonsultasi.isNotEmpty()) null else "Tanggal tidak boleh kosong",
            status = if (event.status.isNotEmpty()) null else "Status tidak boleh kosong",
        )
        uiState = uiState.copy(isEntryValid = errorState)
        return errorState.isValid()
    }

    fun saveData(){
        val currentEvent = uiState
            .jadwalEvent

        if (validateFields()){
            viewModelScope.launch {
                try{
                    repositoryApp.insertJadwal(currentEvent.toJadwalEntity())
                    uiState = uiState.copy(
                        snackBarMessage = "Data berhasil disimpan",
                        jadwalEvent = JadwalEvent(),
                        isEntryValid = FormErrorStateJadwal()
                    )
                } catch (e: Exception){
                    uiState = uiState.copy(
                        snackBarMessage = "Data gagal disimpan"
                    )
                }
            }
        } else{
            uiState = uiState.copy(
                snackBarMessage = "Input tidak valid. Periksa kembali data anda"
            )
        }
    }
    fun reseSnackBarMessage(){
        uiState = uiState.copy(snackBarMessage = null)
    }
}

data class JadwalUIState(
    val jadwalEvent: JadwalEvent = JadwalEvent(),
    val isEntryValid: FormErrorStateJadwal = FormErrorStateJadwal(),
    val snackBarMessage: String? = null,
    val dokterList: List<Dokter> = emptyList()
)

data class FormErrorStateJadwal(
    val namaDokter: String? = null,
    val namaPasien: String? = null,
    val noHp: String? = null,
    val tanggalKonsultasi: String? = null,
    val status: String? = null
){
    fun isValid(): Boolean {
        return namaDokter == null
                && namaPasien == null
                && noHp == null
                && tanggalKonsultasi == null
                && status == null
    }
}

data class JadwalEvent(
    val id: Int = 0,
    val namaDokter: String = "",
    val namaPasien: String = "",
    val noHp: String = "",
    val tanggalKonsultasi: String = "",
    val status: String = ""
)

fun JadwalEvent.toJadwalEntity(): Jadwal = Jadwal(
    id = id,
    namaDokter = namaDokter,
    namaPasien = namaPasien,
    noHp = noHp,
    tanggalKonsultasi = tanggalKonsultasi,
    status = status
)