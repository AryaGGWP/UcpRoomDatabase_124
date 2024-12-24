package com.example.ucp2pam.ui.customwidget

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.example.ucp2pam.R

@Composable
fun BottomAppBar(
    onAddJadwal: () -> Unit = {},
    onHomeClick: () -> Unit = {},
    onAddDokter: () -> Unit = {},
    showFormAddClick: Boolean = true,
    showHomeClick: Boolean = true,
    showJadwalClick: Boolean = true,
    showTambahClick: Boolean = true,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        contentAlignment = Alignment.Center,
    ) {
        Surface(
            modifier = modifier,
            color = colorResource(id = R.color.topbar),
            shadowElevation = 4.dp,
            shape = RoundedCornerShape(topEnd = 16.dp, topStart = 16.dp)
        ) {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp, horizontal = 70.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                if(showHomeClick) {
                    IconButton(onClick = onHomeClick) {
                        Icon(
                            imageVector = Icons.Default.Home,
                            contentDescription = "Home",
                            tint = colorResource(id = R.color.white)
                        )
                    }
                }else {
                    Spacer(modifier = Modifier.size(48.dp))
                }
                if (showTambahClick){
                    IconButton(
                        onClick = onAddDokter,) {
                        Icon(imageVector = if(showFormAddClick) Icons.Default.AddCircle else Icons.Default.Home,
                            contentDescription = if(showFormAddClick) "Home" else "Add",
                            tint = Color.White
                        )
                    }
                }
                if(showJadwalClick) {
                    IconButton(onClick = onAddJadwal) {
                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = "Home Jadwal",
                            tint = colorResource(id = R.color.white)
                        )
                    }
                }
            }
        }
    }
}