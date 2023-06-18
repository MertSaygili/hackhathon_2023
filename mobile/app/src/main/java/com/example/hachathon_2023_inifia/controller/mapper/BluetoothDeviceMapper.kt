package com.example.hachathon_2023_inifia.controller.mapper

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import com.example.hachathon_2023_inifia.domain.models.BluetoothDeviceDomain

@SuppressLint("MissingPermission")
fun BluetoothDevice.toBluetoothDeviceDomain(): BluetoothDeviceDomain {
    return BluetoothDeviceDomain(
        name = name,
        address = address
    )
}