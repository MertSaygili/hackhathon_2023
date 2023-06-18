package com.example.hachathon_2023_inifia.presentation.components.screens

import android.content.Context
import android.content.Intent
import android.provider.Settings
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.hachathon_2023_inifia.R
import com.example.hachathon_2023_inifia.domain.models.BluetoothDevice
import com.example.hachathon_2023_inifia.presentation.components.component.*
import com.example.hachathon_2023_inifia.presentation.viewmodels.BluetoothUiState
import com.example.hachathon_2023_inifia.utils.Constants

@Composable
fun HomeScreen(
    state: BluetoothUiState,
    onStartScan: () -> Unit,
    onStopScan: () -> Unit,
    connectToDevice: (BluetoothDevice) -> Unit,
    context: Context
) {
    Scaffold() {
        contentPadding ->Box(modifier = Modifier.padding(contentPadding)) {
        Column  (
            modifier = Modifier.fillMaxSize()
        ){
            Box(modifier = Modifier.fillMaxWidth()) {
                Image(painter = painterResource(id = R.drawable.infinia_logo), contentDescription = "logo", contentScale = ContentScale.Crop)
            }
            Column() {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 20.dp)
                ) {
                    BluetoothDeviceList(
                        context = context,
                        devices = state.devices,
                        connectToDevice = connectToDevice,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                    )
                    Row(modifier = Modifier.fillMaxWidth().padding(bottom = 10.dp), horizontalArrangement = Arrangement.Center) {
                        CustomFloatingActionButton ("Scan", Icons.Default.Search, "Scan Nearby Devices"){
                            onStartScan()
                        }
                        CustomFloatingActionButton ("Stop Scan", Icons.Default.Close, "Stop Scanning"){
                            onStopScan()
                        }
                    }
                }
            }
        } }
    }

}
@Composable
fun BluetoothDeviceList(
    context: Context,
    devices: List<BluetoothDevice>,
    connectToDevice: (BluetoothDevice) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(modifier = modifier) {

        item { Text(Constants.nearby_raspberrypi_apps) }
        items(devices) { device ->
            var deviceName: String = ""
            deviceName = device.name ?: Constants.unknown_device
            CustomDeviceCard(deviceName = deviceName, deviceMacAddress = device.address) {
                context.startActivity(Intent(Settings.ACTION_BLUETOOTH_SETTINGS))
            }
        }

    }
}