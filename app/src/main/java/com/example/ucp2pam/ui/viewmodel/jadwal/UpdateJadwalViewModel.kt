package com.example.ucp2pam.ui.viewmodel.jadwal

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp2pam.data.entity.Jadwal
import com.example.ucp2pam.repository.RepositoryApp
import com.example.ucp2pam.ui.navigation.DestinasiUpdateJadwal
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class UpdateJadwalViewModel(
    savedStateHandle: SavedStateHandle,
    private val repositoryApp: RepositoryApp
): ViewModel() {
    var updateUIState by mutableStateOf(JadwalUIState())
    private val _id: Int = checkNotNull(savedStateHandle[DestinasiUpdateJadwal.ID])

    init{
        loadDokterList()
        viewModelScope.launch {
            updateUIState = repositoryApp.getJadwal(_id)
                .filterNotNull()
                .first()
                .toUIStateMhs()
        }
    }

    private fun loadDokterList() {
        viewModelScope.launch {
            repositoryApp.getAllDokter()
                .collect { dokter ->
                    updateUIState = updateUIState.copy(dokterList = dokter)
                }
        }
    }
    fun updateState(JadwalEvent: JadwalEvent) {
        updateUIState = updateUIState.copy(
            jadwalEvent = JadwalEvent,
        )
    }
    fun validateFields(): Boolean {
        val event = updateUIState.jadwalEvent
        val errorState = FormErrorStateJadwal(
            namaDokter = if (event.namaDokter.isNotEmpty()) null else "Nama Dokter tidak boleh kosong",
            namaPasien = if (event.namaPasien.isNotEmpty()) null else "Nama Pasien tidak boleh kosong",
            noHp = if (event.noHp.isNotEmpty()) null else "Nomor Hp tidak boleh kosong",
            tanggalKonsultasi = if (event.tanggalKonsultasi.isNotEmpty()) null else "Tanggal tidak boleh kosong",
            status = if (event.status.isNotEmpty()) null else "Status tidak boleh kosong",
        )
        updateUIState = updateUIState.copy(isEntryValid = errorState)
        return errorState.isValid()
    }
    suspend fun updateData(){
        val currentEvent = updateUIState.jadwalEvent

        if (validateFields()){
            try {
                repositoryApp.updateJadwal(currentEvent.toJadwalEntity())
                updateUIState = updateUIState.copy(
                    snackBarMessage = "Data berhasil diupdate",
                    jadwalEvent = JadwalEvent(),
                    isEntryValid = FormErrorStateJadwal()
                )
                println("snackbarMassage diatur: ${updateUIState.snackBarMessage}")
            } catch (e: Exception){
                updateUIState = updateUIState.copy(
                    snackBarMessage = "Data gagal diupdate"
                )
            }
        } else {
            updateUIState = updateUIState.copy(
                snackBarMessage = "Data gagal diupdate"
            )
        }

    }
    fun resetSnackBarMessage(){
        updateUIState = updateUIState.copy(snackBarMessage = null)
    }
}

fun Jadwal.toUIStateMhs(): JadwalUIState = JadwalUIState(
    jadwalEvent = this.toDetailUiEvent(),
)