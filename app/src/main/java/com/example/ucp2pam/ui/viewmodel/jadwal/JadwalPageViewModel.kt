package com.example.ucp2pam.ui.viewmodel.jadwal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp2pam.data.entity.Jadwal
import com.example.ucp2pam.repository.RepositoryApp
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn

class JadwalPageViewModel(private val repositoryApp: RepositoryApp) : ViewModel(){
    val homeUiJadwalState: StateFlow<HomeUiJadwalState> = repositoryApp.getAllJadwal()
        .filterNotNull()
        .map{
            HomeUiJadwalState(
                listjadwal = it.toList(),
                isLoading = false
            )
        }
        .onStart {
            emit(HomeUiJadwalState(isLoading = true))
            delay(900)
        }
        .catch {
            emit(
                HomeUiJadwalState(
                    isLoading = false,
                    isError = true,
                    errorMessage = it.message ?: "Terjadi Kesalahan"
                )
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = HomeUiJadwalState(isLoading = true)
        )
}

data class HomeUiJadwalState(
    val listjadwal: List<Jadwal> = listOf(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = ""
)