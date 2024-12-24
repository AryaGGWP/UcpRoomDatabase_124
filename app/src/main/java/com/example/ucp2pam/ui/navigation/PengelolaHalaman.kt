package com.example.ucp2pam.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.ucp2pam.ui.view.dokter.HomeDokterView
import com.example.ucp2pam.ui.view.dokter.AddDokterView
import com.example.ucp2pam.ui.view.jadwal.AddJadwalView
import com.example.ucp2pam.ui.view.jadwal.DetailJadwalView
import com.example.ucp2pam.ui.view.jadwal.HomeJadwalView
import com.example.ucp2pam.ui.view.jadwal.UpdateJadwalView

@Composable
fun PengelolaHalaman(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
){
    NavHost(navController = navController, startDestination = DestinasiHome.route)
    {
        composable(route = DestinasiHome.route)
        {
            HomeDokterView(
                onAddDokter = {
                    navController.navigate(DestinasiTambahDokter.route)
                },
                onAddJadwal = {
                    navController.navigate(DestinasiHomeJadwal.route)
                },
                modifier = modifier)
        }
        composable(
            route = DestinasiTambahDokter.route
        ){
            AddDokterView(
                onBack = {
                    navController.popBackStack()
                },
                onNavigate = {
                    navController.popBackStack()
                },
                onAddDokter = {
                    navController.navigate(DestinasiHome.route)
                },
                modifier = modifier)
        }
        composable(
            route = DestinasiHomeJadwal.route
        ){
            HomeJadwalView(
                onBack = {
                    navController.popBackStack()
                },
                onHomeClick = {
                    navController.navigate(DestinasiHome.route)
                },
                onAddJadwal = {
                    navController.navigate(DestinasiAddJadwal.route)
                },
                onDetailClick = { id ->
                    navController.navigate("${DestinasiDetailJadwal.route}/$id")
                    println("PengelolaHalaman: id = $id")
                },
                modifier = modifier)
        }
        composable(
            route = DestinasiAddJadwal.route
        ){
            AddJadwalView(
                onBack = {
                    navController.popBackStack()
                },
                onNavigate = {
                    navController.popBackStack()
                },
                onAddDokter = {
                    navController.navigate(DestinasiHomeJadwal.route)
                },
                modifier = modifier)
        }
        composable(
            DestinasiDetailJadwal.routesWithArg,
            arguments = listOf(
                navArgument(DestinasiDetailJadwal.ID){
                    type = NavType.IntType
                }
            )
        ){
            val id = it.arguments?.getInt(DestinasiDetailJadwal.ID)
            id?.let { id ->
                DetailJadwalView(
                    onBack = {
                        navController.popBackStack()
                    },
                    onEditClick = {
                        navController.navigate("${DestinasiUpdateJadwal.route}/$id")
                        println("PengelolaHalaman: id = $id")
                    },
                    onAddDokter = {
                        navController.navigate(DestinasiHomeJadwal.route)
                    },
                    modifier = modifier,
                    onDeleteClick = {
                        navController.popBackStack()
                    }
                )
            }
        }
        composable(
            DestinasiUpdateJadwal.routesWithArg,
            arguments = listOf(
                navArgument(DestinasiUpdateJadwal.ID){
                    type = NavType.IntType
                }
            )
        ){
            UpdateJadwalView(
                onBack = {
                    navController.popBackStack()
                },
                onNavigate = {
                    navController.popBackStack()
                },
                onAddDokter = {
                    navController.navigate(DestinasiHomeJadwal.route)
                },
                modifier = modifier
            )
        }
    }
}