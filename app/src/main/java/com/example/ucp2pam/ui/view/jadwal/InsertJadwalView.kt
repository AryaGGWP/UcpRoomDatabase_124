package com.example.ucp2pam.ui.view.jadwal

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ucp2pam.R
import com.example.ucp2pam.data.entity.Dokter
import com.example.ucp2pam.ui.customwidget.BottomAppBar
import com.example.ucp2pam.ui.customwidget.DynamicSelectedField
import com.example.ucp2pam.ui.customwidget.TopAppBar
import com.example.ucp2pam.ui.viewmodel.PenyediaViewModel
import com.example.ucp2pam.ui.viewmodel.jadwal.AddJadwalViewModel
import com.example.ucp2pam.ui.viewmodel.jadwal.FormErrorStateJadwal
import com.example.ucp2pam.ui.viewmodel.jadwal.JadwalEvent
import com.example.ucp2pam.ui.viewmodel.jadwal.JadwalUIState
import kotlinx.coroutines.launch

@Composable
fun AddJadwalView(
    onBack: () -> Unit,
    onNavigate: () -> Unit,
    onAddDokter: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AddJadwalViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val uiState = viewModel.uiState
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(uiState.snackBarMessage) {
        uiState.snackBarMessage?.let { message ->
            coroutineScope.launch {
                snackbarHostState.showSnackbar(message)
                viewModel.reseSnackBarMessage()
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                judul = "Isi Jadwal Anda Disini!",
                judulKecil = "",
                judul2 = "Tambah Jadwal",
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
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
        ) {
            InsertBodyJadwal(
                uiState = uiState,
                onValueChange = { updateEvent ->
                    viewModel.updateState(updateEvent)
                },
                onClick = {
                    coroutineScope.launch {
                        viewModel.saveData()
                    }
                    onNavigate()
                }
            )
        }
    }
}

@Composable
fun FormJadwal(
    jadwalEvent: JadwalEvent = JadwalEvent(),
    onValueChange: (JadwalEvent) -> Unit = {},
    errorState: FormErrorStateJadwal = FormErrorStateJadwal(),
    dokterList: List<Dokter>,
    modifier: Modifier = Modifier
) {
    Card {
        Column(
            modifier = Modifier.fillMaxWidth()
        ){

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = jadwalEvent.namaPasien,
                onValueChange = {
                    onValueChange(jadwalEvent.copy(namaPasien = it))
                },
                label = { Text("Nama Pasien", modifier = Modifier, color = colorResource(id = R.color.black)) },
                isError = errorState.namaPasien != null,
                placeholder = { Text("Masukkan Nama Anda") },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = colorResource(id = R.color.black),
                    focusedTextColor = colorResource(id = R.color.black),
                    unfocusedTextColor = colorResource(id = R.color.black),
                    cursorColor = colorResource(id = R.color.black)
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                singleLine = true,
                shape = RectangleShape,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Person,
                        contentDescription = "",
                        tint = colorResource(id = R.color.black)
                    )
                }
            )
            Text(
                text = errorState.namaPasien ?: "",
                color = Color.Red
            )
            DynamicSelectedField(
                selectedValue = jadwalEvent.namaDokter,
                options = dokterList.map { it.nama }.also {
                    println("Dropdown options: $it")
                },
                label = "Nama Dokter",
                placeholder = "Pilih Dokter Spesialis",
                onValueChangedEvent = {
                    onValueChange(jadwalEvent.copy(namaDokter = it))
                },
                modifier = Modifier.fillMaxWidth(),
            )
            Text(
                text = errorState.namaDokter ?: "",
                color = Color.Red
            )

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = jadwalEvent.noHp,
                onValueChange = {
                    onValueChange(jadwalEvent.copy(noHp = it))
                },
                label = { Text("Nomor Telepon", modifier = Modifier, color = colorResource(id = R.color.black)) },
                isError = errorState.noHp != null,
                placeholder = { Text("Masukkan Nomor Telepon Anda") },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = colorResource(id = R.color.black),
                    focusedTextColor = colorResource(id = R.color.black),
                    unfocusedTextColor = colorResource(id = R.color.black),
                    cursorColor = colorResource(id = R.color.black)
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                singleLine = true,
                shape = RectangleShape,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Phone,
                        contentDescription = "",
                        tint = colorResource(id = R.color.black)
                    )
                }
            )
            Text(
                text = errorState.noHp ?: "",
                color = Color.Red
            )

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = jadwalEvent.tanggalKonsultasi,
                onValueChange = {
                    onValueChange(jadwalEvent.copy(tanggalKonsultasi = it))
                },
                label = { Text("Tanggal Konsultasi",
                    modifier = Modifier,
                    color = colorResource(id = R.color.black)) },
                isError = errorState.tanggalKonsultasi != null,
                placeholder = { Text("Masukkan Tanggal Konsultasi") },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = colorResource(id = R.color.black),
                    focusedTextColor = colorResource(id = R.color.black),
                    unfocusedTextColor = colorResource(id = R.color.black),
                    cursorColor = colorResource(id = R.color.black)
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                singleLine = true,
                shape = RectangleShape,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.DateRange,
                        contentDescription = "",
                        tint = colorResource(id = R.color.black)
                    )
                }
            )
            Text(
                text = errorState.tanggalKonsultasi ?: "",
                color = Color.Red
            )


            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = jadwalEvent.status,
                onValueChange = {
                    onValueChange(jadwalEvent.copy(status = it))
                },
                label = { Text("Status", modifier = Modifier, color = colorResource(id = R.color.black)) },
                isError = errorState.status != null,
                placeholder = { Text("Masukkan Status") },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = colorResource(id = R.color.black),
                    focusedTextColor = colorResource(id = R.color.black),
                    unfocusedTextColor = colorResource(id = R.color.black),
                    cursorColor = colorResource(id = R.color.black)
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                singleLine = true,
                shape = RectangleShape,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.AddCircle,
                        contentDescription = "",
                        tint = colorResource(id = R.color.black)
                    )
                }
            )
            Text(
                text = errorState.status ?: "",
                color = Color.Red
            )
        }
    }
}

@Composable
fun InsertBodyJadwal(
    modifier: Modifier = Modifier,
    onValueChange: (JadwalEvent) -> Unit,
    uiState: JadwalUIState,
    onClick: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        FormJadwal(
            jadwalEvent = uiState.jadwalEvent,
            onValueChange = onValueChange,
            errorState = uiState.isEntryValid,
            dokterList = uiState.dokterList,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        IconButton(
            onClick = onClick,
            modifier = Modifier
                .size(56.dp)
                .background(colorResource(id = R.color.teal_200), CircleShape) // Latar belakang bulat
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.Send,
                contentDescription = "Simpan",
                tint = Color.White,
                modifier = Modifier.size(32.dp)
            )
        }
    }
}