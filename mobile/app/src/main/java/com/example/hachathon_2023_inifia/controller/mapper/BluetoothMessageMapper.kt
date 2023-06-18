package com.example.hachathon_2023_inifia.controller.mapper

import com.example.hachathon_2023_inifia.domain.models.BluetoothMessage

fun BluetoothMessage.toByteArray() : ByteArray {
    return byteFile.toByteArray()
}

fun String.toByteArray() : ByteArray{
    return this.encodeToByteArray()
}
