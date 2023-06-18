package com.example.hachathon_2023_inifia.presentation.viewmodels

import com.example.hachathon_2023_inifia.domain.models.BluetoothDevice
import com.example.hachathon_2023_inifia.domain.models.BluetoothMessage

data class BluetoothUiState (
    // devices control
    val scannedDevices: List<BluetoothDevice> = emptyList(),
    val pairedDevices: List<BluetoothDevice> = emptyList(),
    val devices: List<BluetoothDevice> = emptyList(),

    // device connection
    val isConnected: Boolean = false,
    val isConnecting: Boolean = false,

    val errorOccur: Boolean = false,
    // error statement
    val errorMessage: String? = null,
    // bluetooth messages
    val messages: List<BluetoothMessage> = emptyList()
)
