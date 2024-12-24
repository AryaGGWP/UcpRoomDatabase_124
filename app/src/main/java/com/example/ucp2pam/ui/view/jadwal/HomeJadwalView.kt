package com.example.ucp2pam.ui.view.jadwal

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ucp2pam.data.entity.Jadwal
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ucp2pam.R
import com.example.ucp2pam.ui.customwidget.BottomAppBar
import com.example.ucp2pam.ui.customwidget.TopAppBar
import com.example.ucp2pam.ui.viewmodel.PenyediaViewModel
import com.example.ucp2pam.ui.viewmodel.jadwal.HomeUiJadwalState
import com.example.ucp2pam.ui.viewmodel.jadwal.JadwalPageViewModel
import kotlinx.coroutines.launch

@Composable
fun HomeJadwalView(
    viewModel: JadwalPageViewModel = viewModel(factory = PenyediaViewModel.Factory),
    onAddJadwal: () -> Unit = {},
    onHomeClick: () -> Unit = {},
    onBack: () -> Unit,
    onDetailClick: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                judul = "Ayo Sehat Bareng!",
                judulKecil = "Aplikasi Layanan Kesehatan Terpadu Anda",
                judul2 = "Daftar Jadwal Hari Ini",
                showBackButton = false,
                showProfile = true,
                onBack = onBack,
                modifier = modifier
            )
        },
        bottomBar = {
            BottomAppBar(
                onHomeClick = onHomeClick,
                onAddDokter = onAddJadwal,
                onAddJadwal = { },
                showHomeClick = true,
                showJadwalClick = true,
                showTambahClick = true,
            )
        },
    ){ innerPadding ->
        val homeUiJadwalState by viewModel.homeUiJadwalState.collectAsState()

        BodyHomeJadwalView(
            homeUiJadwalState = homeUiJadwalState,
            onClick = {
                onDetailClick(it)
            },
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun BodyHomeJadwalView(
    homeUiJadwalState: HomeUiJadwalState,
    onClick: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    when{
        homeUiJadwalState.isLoading -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                CircularProgressIndicator()
            }
        }
        homeUiJadwalState.isError -> {
            LaunchedEffect(homeUiJadwalState.errorMessage) {
                homeUiJadwalState.errorMessage?.let { message ->
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar(message)
                    }
                }
            }
        }
        homeUiJadwalState.listjadwal.isEmpty() ->{
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                Text(
                    text = "Tidak ada data Jadwal Terkini",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = modifier.padding(16.dp)
                )
            }
        }
        else -> {
            ListJadwal(
                listJadwal = homeUiJadwalState.listjadwal,
                onClick = {
                    onClick(it)
                    println(it)
                },
                modifier = modifier
            )
        }
    }
}

@Composable
fun ListJadwal(
    listJadwal: List<Jadwal>,
    modifier: Modifier = Modifier,
    onClick: (String) -> Unit = {}
){
    LazyColumn(
        modifier = modifier
    ) {
        items(
            items = listJadwal,
            itemContent = {jadwal ->
                JadwalCard(
                    jadwal = jadwal,
                    onClick = {onClick(jadwal.id.toString())}
                )
            }
        )
    }
}

@Composable
fun JadwalCard(
    jadwal: Jadwal,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 6.dp)
            .border(1.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.background),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorResource(id = R.color.purple_200)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Pasien",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .size(55.dp)
                        .clip(CircleShape)
                        .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = jadwal.namaPasien,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Text(
                        text = jadwal.status,
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.secondary,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            HorizontalDivider(thickness = 1.dp, color = MaterialTheme.colorScheme.secondary)
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "Dokter",
                    tint = MaterialTheme.colorScheme.secondary
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = " : ${jadwal.namaDokter}",
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.Medium
                )
            }
            Spacer(modifier = Modifier.height(4.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = "Tanggal",
                    tint = MaterialTheme.colorScheme.secondary
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = " : ${jadwal.tanggalKonsultasi}",
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.Medium
                )
            }
            Spacer(modifier = Modifier.height(4.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    imageVector = Icons.Default.Phone,
                    contentDescription = "Kontak",
                    tint = MaterialTheme.colorScheme.secondary
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = " : ${jadwal.noHp}",
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}
