package com.example.hachathon_2023_inifia.domain

import com.example.hachathon_2023_inifia.domain.models.BluetoothMessage

sealed interface ConnectionResult{
    object ConnectionEstablished : ConnectionResult
    data class TransferSucceeded(val message: BluetoothMessage) : ConnectionResult
    data class Error(val message: String) : ConnectionResult
}
