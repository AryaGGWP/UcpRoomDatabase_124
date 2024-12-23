package com.example.ucp2pam

import android.app.Application
import com.example.ucp2pam.DependenciesInjection.ContainerApp

class AyoSehatApp : Application() {
    lateinit var containerApp: ContainerApp // Fungsinya untuk menyimpan instance

    override fun onCreate() {
        super.onCreate()
        containerApp = ContainerApp(this) // Membuat Instamnce ContainerApp
        // Instance adalah object yang dibuat dari class
    }
}