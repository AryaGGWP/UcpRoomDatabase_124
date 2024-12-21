package com.example.ucp2pam.DependenciesInjection

import android.content.Context
import com.example.ucp2pam.data.database.AyoSehatDatabase
import com.example.ucp2pam.repository.LocalRepositoryDkt
import com.example.ucp2pam.repository.LocalRepositoryJdw
import com.example.ucp2pam.repository.RepositoryDkt
import com.example.ucp2pam.repository.RepositoryJdw

interface InterfaceContainerApp{
    val repositoryDkt: RepositoryDkt
    val repositoryJdw: RepositoryJdw
}

class ContainerApp(private val context: Context) : InterfaceContainerApp{
    override val repositoryDkt: RepositoryDkt by lazy{
        LocalRepositoryDkt(AyoSehatDatabase.getDatabase(context).dokterDao())
    }
    override val repositoryJdw: RepositoryJdw by lazy {
        LocalRepositoryJdw(AyoSehatDatabase.getDatabase(context).jadwalDao())
    }
}
