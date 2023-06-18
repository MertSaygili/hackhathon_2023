package com.example.hachathon_2023_inifia.presentation.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hachathon_2023_inifia.domain.ConnectionResult
import com.example.hachathon_2023_inifia.domain.IBluetoothController
import com.example.hachathon_2023_inifia.domain.models.BluetoothDevice
import com.example.hachathon_2023_inifia.domain.models.BluetoothDeviceDomain
import com.example.hachathon_2023_inifia.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class BluetoothViewModel @Inject constructor(private val bluetoothController: IBluetoothController, @ApplicationContext application: Context): ViewModel() {
    private val _state = MutableStateFlow(BluetoothUiState())

    val state = combine(
        bluetoothController.scannedDevices,
        bluetoothController.pairedDevices,
        _state
    ) { scannedDevices, pairedDevices, state ->
        state.copy(
            scannedDevices = scannedDevices,
            pairedDevices = pairedDevices,
            messages = if (state.isConnected) state.messages else emptyList()
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), _state.value)


    private var deviceConnectionJob: Job? = null
    private var device: BluetoothDeviceDomain? = null
    init {
        bluetoothController.isConnected.onEach { isConnected ->
            _state.update { it.copy(isConnected = isConnected) }}.launchIn(viewModelScope)

        bluetoothController.errors.onEach { error ->
            _state.update { it.copy(errorMessage = error) }
        }.launchIn(viewModelScope)
    }

    fun startScan() {
        bluetoothController.startDiscovery()
        filterDevices()
    }

    // for discovery -- stops discovery
    fun stopScan() {
        bluetoothController.stopDiscovery()
    }

    // connects devices
    fun connectToDevice(device: BluetoothDeviceDomain) {
        bluetoothController.connectToDevice(device)
        this.device = device
    }

    // sends message to other device
    fun sendMessage(message: String) {
        bluetoothController.trySendMessage(message)
    }
    private fun filterDevices(){
        var devices: List<BluetoothDevice> = emptyList()
        for (device in bluetoothController.scannedDevices.value + bluetoothController.pairedDevices.value) {
            if(device.name == Constants.device_name){
                Log.d("Succes", "X")
                devices = devices + device
            }
        }

        _state.update {
            it.copy(
                devices = devices
            )
        }
    }
    private fun Flow<ConnectionResult>.listen(): Job {
        return onEach { result ->
            when(result) {
                ConnectionResult.ConnectionEstablished -> {
                    _state.update { it.copy(
                        isConnected = true,
                        isConnecting = false,
                        errorMessage = null,
                        errorOccur = false
                    ) }
                }
                is ConnectionResult.TransferSucceeded -> {
                    _state.update { it.copy(
                        messages = it.messages + result.message
                    ) }

                }
                is ConnectionResult.Error -> {
                    _state.update { it.copy(
                        isConnected = false,
                        isConnecting = false,
                        errorMessage = result.message,
                        errorOccur = true,
                    ) }
                }
            }
        }
            .catch { throwable ->
                bluetoothController.closeConnection()
                _state.update { it.copy(
                    isConnected = false,
                    isConnecting = false,
                    errorOccur = false,
                ) }
            }
            .launchIn(viewModelScope)
    }

    // clear all bluetooth devices -- not used in app
    override fun onCleared() {
        super.onCleared()
        bluetoothController.release()
    }

    companion object{
        const val SERVICE_UUID = "57dc2ee4-f48e-11ed-a05b-0242ac120003"
    }

}