package com.example.ucp2pam.DependenciesInjection

import android.content.Context
import com.example.ucp2pam.data.database.AyoSehatDatabase
import com.example.ucp2pam.repository.LocalRepositoryApp
import com.example.ucp2pam.repository.RepositoryApp

interface InterfaceContainerApp{
    val repositoryApp: RepositoryApp
}

class ContainerApp (private val context: Context) : InterfaceContainerApp {
    override val repositoryApp: RepositoryApp by lazy{
        LocalRepositoryApp(AyoSehatDatabase.getDatabase(context).dokterDao(),
            AyoSehatDatabase.getDatabase(context).jadwalDao())
    }
}