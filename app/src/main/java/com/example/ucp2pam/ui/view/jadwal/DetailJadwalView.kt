package com.example.ucp2pam.ui.view.jadwal

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ucp2pam.R
import com.example.ucp2pam.data.entity.Jadwal
import com.example.ucp2pam.ui.customwidget.BottomAppBar
import com.example.ucp2pam.ui.customwidget.TopAppBar
import com.example.ucp2pam.ui.viewmodel.PenyediaViewModel
import com.example.ucp2pam.ui.viewmodel.jadwal.DetailJadwalViewModel
import com.example.ucp2pam.ui.viewmodel.jadwal.DetailUiState
import com.example.ucp2pam.ui.viewmodel.jadwal.toJadwalEntity

@Composable
fun DetailJadwalView(
    modifier: Modifier = Modifier,
    viewModel: DetailJadwalViewModel = viewModel(factory = PenyediaViewModel.Factory),
    onBack: () -> Unit = {},
    onEditClick: (String) -> Unit = {},
    onDeleteClick: () -> Unit = {},
    onAddDokter: () -> Unit = {},
){
    Scaffold(
        topBar = {
            TopAppBar(
                judul = "Jadwal Anda Akan Ditampilkan Disini",
                judulKecil = "",
                judul2 = "Detail Jadwal ",
                showBackButton = true,
                showProfile = false,
                onBack = onBack,
                modifier = Modifier
            )
        },
        bottomBar = {
            BottomAppBar(
                onAddDokter = onAddDokter,
                showFormAddClick = false,
                showTambahClick = true,
                showJadwalClick = false,
                showHomeClick = false,
            )
        },
    ){innerPadding ->
        val detailUiState by viewModel.detailUiState.collectAsState()

        BodyDetailJadwal(
            modifier = Modifier.padding(innerPadding),
            detailUiState = detailUiState,
            onEditClick = {
                onEditClick(viewModel.detailUiState.value.detailUiEvent.id.toString())
            },
            onDeleteClick = {
                viewModel.deleteJadwal()
                onDeleteClick
            }
        )
    }
}

@Composable
fun BodyDetailJadwal(
    modifier: Modifier = Modifier,
    detailUiState: DetailUiState = DetailUiState(),
    onDeleteClick: () -> Unit = {},
    onEditClick: (String) -> Unit = {},
){
    var deleteConfirmationRequared by rememberSaveable { mutableStateOf(false) }
    when{
        detailUiState.isLoading ->{
            Box(
                modifier = modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ){
                CircularProgressIndicator()
            }
        }
        detailUiState.isUiEventNotEmpty ->{
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ){
                ItemDetailJadwal(
                    jadwal = detailUiState.detailUiEvent.toJadwalEntity(),
                    modifier = modifier.padding(bottom = 0.dp)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    IconButton(
                        onClick = { onEditClick(detailUiState.detailUiEvent.id.toString()) },
                        modifier = Modifier
                            .size(50.dp)
                            .background(color = colorResource(id = R.color.teal_200), CircleShape)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Edit",
                            tint = colorResource(id = R.color.white)
                        )
                    }
                    IconButton(
                        onClick = {
                            deleteConfirmationRequared = true
                        },
                        modifier = Modifier
                            .size(50.dp)
                            .background(color = colorResource(id = R.color.teal_200), CircleShape)
                    ){
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete",
                            tint = colorResource(id = R.color.white)
                        )
                    }
                }
                if(deleteConfirmationRequared){
                    DeleteConfirmationDialog(
                        onDeleteConfirm = {
                            deleteConfirmationRequared = false
                            onDeleteClick()
                        },
                        onDeleteCancel = {
                            deleteConfirmationRequared = false
                        },
                        modifier = modifier.padding(8.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun DetailJadwal(
    icon: ImageVector,
    judul: String,
    isinya: String,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = colorResource(id = R.color.icon),
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(
                text = judul,
                fontSize = 12.sp,
                color = colorResource(id = R.color.black)
            )
            Text(
                text = isinya,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = colorResource(id = R.color.black)
            )
        }
    }
}

@Composable
fun ItemDetailJadwal(
    jadwal: Jadwal,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 0.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
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
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = jadwal.namaPasien,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(id = R.color.black)
                )
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = "Dokter",
                    tint = colorResource(id = R.color.icon)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            DetailJadwal(
                icon = Icons.Filled.Info,
                judul = "ID Jadwal",
                isinya = jadwal.id.toString(),
            )
            DetailJadwal(
                icon = Icons.Filled.AccountCircle,
                judul = "Nama Dokter",
                isinya = jadwal.namaDokter,
            )
            DetailJadwal(
                icon = Icons.Filled.Call,
                judul = "No Telepon",
                isinya = jadwal.noHp
            )
            DetailJadwal(
                icon = Icons.Filled.DateRange,
                judul = "Tanggal Konsultasi",
                isinya = jadwal.tanggalKonsultasi
            )
            DetailJadwal(
                icon = Icons.Filled.AddCircle,
                judul = "Status",
                isinya = jadwal.status

            )
        }
    }
}

@Composable
private fun DeleteConfirmationDialog(
    onDeleteConfirm: () -> Unit,
    onDeleteCancel: () -> Unit,
    modifier: Modifier = Modifier
){
    AlertDialog(onDismissRequest = {},
        title = {
            Text("HAPUS DATA",
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            )},
        textContentColor = Color.Red,
        text = { Text("Apakah anda yakin ingin menghapus data ini?") },
        modifier = modifier,
        dismissButton = {
            TextButton(onClick = onDeleteCancel
            ) {
                Text("Cancel")
            }
        },
        confirmButton = {
            TextButton(onClick = onDeleteConfirm,
                colors = ButtonDefaults.textButtonColors(
                    contentColor = Color.Red
                )) {
                Text("Yes")
            }
        }
    )
}