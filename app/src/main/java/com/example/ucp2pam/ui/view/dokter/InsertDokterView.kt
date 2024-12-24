package com.example.ucp2pam.ui.view.dokter

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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.LocationOn
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
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ucp2pam.R
import com.example.ucp2pam.ui.customwidget.BottomAppBar
import com.example.ucp2pam.ui.customwidget.DynamicSelectedField
import com.example.ucp2pam.ui.customwidget.TopAppBar
import com.example.ucp2pam.ui.viewmodel.PenyediaViewModel
import com.example.ucp2pam.ui.viewmodel.dokter.AddDokterViewModel
import com.example.ucp2pam.ui.viewmodel.dokter.DktrUIState
import com.example.ucp2pam.ui.viewmodel.dokter.DokterEvent
import com.example.ucp2pam.ui.viewmodel.dokter.FormErrorState
import kotlinx.coroutines.launch

@Composable
fun AddDokterView(
    onBack: () -> Unit,
    onAddDokter : () -> Unit,
    onNavigate: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AddDokterViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val uiState = viewModel.uiState
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(uiState.snackBarMessage) {
        uiState.snackBarMessage?.let { message ->
            coroutineScope.launch {
                snackbarHostState.showSnackbar(message)
                viewModel.resetSnackBarMessage()
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                judul = "Form Tambah Dokter",
                judulKecil = "",
                judul2 = "Tambah Dokter",
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
            InsertBodyDokter(
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
fun FormDokter(
    dokterEvent: DokterEvent = DokterEvent(),
    onValueChange: (DokterEvent) -> Unit = {},
    errorState: FormErrorState = FormErrorState(),
    modifier: Modifier = Modifier
) {
    val spesialisOptions
    = listOf(
        "Dokter Umum",
        "Dokter Tulang",
        "Dokter Persalinan",
        "Dokter Mata",
        "Dokter Gigi",
        "Dokter Kulit")

    Card{
        Column(
            modifier = Modifier.fillMaxWidth()
        ){

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = dokterEvent.nama,
                onValueChange = {
                    onValueChange(dokterEvent.copy(nama = it))
                },
                label = {
                    Text("Nama Dokter",
                        modifier = Modifier,
                        color = colorResource(id = R.color.black)) },
                isError = errorState.nama != null,
                placeholder = { Text("Masukkan Nama Dokter") },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.DarkGray,
                    focusedTextColor = Color.DarkGray,
                    unfocusedTextColor = Color.DarkGray,
                    cursorColor = colorResource(id = R.color.black)),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                singleLine = true,
                shape = RectangleShape,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.AccountCircle,
                        contentDescription = "",
                        tint = colorResource(id = R.color.black)
                    )
                }
            )
            Text(
                text = errorState.nama ?: "",
                color = Color.Red
            )

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = dokterEvent.klinik,
                onValueChange = {
                    onValueChange(dokterEvent.copy(klinik = it))
                },
                label = { Text("Tempat Praktek",
                    modifier = Modifier,
                    color = colorResource(id = R.color.black))
                },
                isError = errorState.klinik != null,
                placeholder = { Text("Masukkan Nama Klinik/Tempat Praktek") },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.DarkGray,
                    focusedTextColor = Color.DarkGray,
                    unfocusedTextColor = Color.DarkGray,
                    cursorColor = colorResource(id = R.color.black)),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                singleLine = true,
                shape = RectangleShape,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.LocationOn,
                        contentDescription = "",
                        tint = colorResource(id = R.color.black)
                    )
                }
            )
            Text(
                text = errorState.klinik ?: "",
                color = Color.Red
            )

            DynamicSelectedField(
                selectedValue = dokterEvent.spesialis,
                options = spesialisOptions,
                label = "Spesialis",
                placeholder = "Pilih Spesialis",
                onValueChangedEvent = {
                    onValueChange(dokterEvent.copy(spesialis = it))
                },
                modifier = Modifier.fillMaxWidth(),
            )
            Text(
                text = errorState.spesialis ?: "",
                color = Color.Red
            )

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = dokterEvent.noHp,
                onValueChange = {
                    onValueChange(dokterEvent.copy(noHp = it))
                },
                label = { Text("No Telepon", modifier = Modifier, color = colorResource(id = R.color.black)) },
                isError = errorState.noHp != null,
                placeholder = { Text("Masukkan No Telepon Anda") },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = colorResource(id = R.color.black),
                    focusedTextColor = colorResource(id = R.color.black),
                    unfocusedTextColor = colorResource(id = R.color.black),
                    cursorColor = colorResource(id = R.color.black)),
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
                value = dokterEvent.jamKerja,
                onValueChange = {
                    onValueChange(dokterEvent.copy(jamKerja = it))
                },
                label = { Text("Jam Kerja", modifier = Modifier, color = colorResource(id = R.color.black)) },
                isError = errorState.jamKerja != null,
                placeholder = { Text("Masukkan Ketersediaan Jam Kerja") },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = colorResource(id = R.color.black),
                    focusedTextColor = colorResource(id = R.color.black),
                    unfocusedTextColor = colorResource(id = R.color.black),
                    cursorColor = colorResource(id = R.color.black)),
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
                text = errorState.jamKerja ?: "",
                color = Color.Red
            )
    }
    }
}

@Composable
fun InsertBodyDokter(
    modifier: Modifier = Modifier,
    onValueChange: (DokterEvent) -> Unit,
    uiState: DktrUIState,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        FormDokter(
            dokterEvent = uiState.dokterEvent,
            onValueChange = onValueChange,
            errorState = uiState.isEntryValid,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        IconButton(
            onClick = onClick,
            modifier = Modifier
                .size(56.dp)
                .background(colorResource(id = R.color.teal_200), CircleShape)
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