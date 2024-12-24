package com.example.ucp2pam.ui.viewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.ucp2pam.AyoSehatApp
import com.example.ucp2pam.ui.viewmodel.dokter.AddDokterViewModel
import com.example.ucp2pam.ui.viewmodel.dokter.HomeDokterViewModel
import com.example.ucp2pam.ui.viewmodel.jadwal.AddJadwalViewModel
import com.example.ucp2pam.ui.viewmodel.jadwal.DetailJadwalViewModel
import com.example.ucp2pam.ui.viewmodel.jadwal.JadwalPageViewModel
import com.example.ucp2pam.ui.viewmodel.jadwal.UpdateJadwalViewModel

object PenyediaViewModel  {
    val Factory = viewModelFactory {
        initializer {
            HomeDokterViewModel(
                AyoSehatApp().containerApp.repositoryApp
            )
        }
        initializer {
            AddDokterViewModel(
                AyoSehatApp().containerApp.repositoryApp
            )
        }
        initializer {
            AddJadwalViewModel(
                AyoSehatApp().containerApp.repositoryApp
            )
        }
        initializer {
            JadwalPageViewModel(
                AyoSehatApp().containerApp.repositoryApp
            )
        }

        initializer {
            DetailJadwalViewModel(
                createSavedStateHandle(),
                AyoSehatApp().containerApp.repositoryApp
            )
        }

        initializer {
            UpdateJadwalViewModel(
                createSavedStateHandle(),
                AyoSehatApp().containerApp.repositoryApp
            )
        }
    }
}

fun CreationExtras.AyoSehatApp(): AyoSehatApp =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as AyoSehatApp)