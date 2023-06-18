package com.example.hachathon_2023_inifia.domain

import com.example.hachathon_2023_inifia.domain.models.BluetoothDevice
import com.example.hachathon_2023_inifia.domain.models.BluetoothMessage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface IBluetoothController {
    val isConnected: StateFlow<Boolean>
    val scannedDevices: StateFlow<List<BluetoothDevice>>
    val pairedDevices: StateFlow<List<BluetoothDevice>>
    val errors: SharedFlow<String>

    fun startDiscovery()
    fun stopDiscovery()
    fun startBluetoothServer() : Flow<ConnectionResult>
    fun connectToDevice(device: BluetoothDevice)
    fun disconnectFromBluetoothDevice(device: BluetoothDevice)
    fun trySendMessage(message: String)
    fun closeConnection()
    fun release()
}