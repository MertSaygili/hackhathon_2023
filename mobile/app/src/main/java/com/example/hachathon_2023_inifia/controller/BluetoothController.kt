package com.example.hachathon_2023_inifia.controller

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothServerSocket
import android.bluetooth.BluetoothSocket
import android.content.Context
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.util.Log
import com.example.hachathon_2023_inifia.controller.mapper.toBluetoothDeviceDomain
import com.example.hachathon_2023_inifia.controller.mapper.toByteArray
import com.example.hachathon_2023_inifia.domain.ConnectionResult
import com.example.hachathon_2023_inifia.domain.IBluetoothController
import com.example.hachathon_2023_inifia.domain.models.BluetoothDeviceDomain
import com.example.hachathon_2023_inifia.domain.models.BluetoothMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.io.OutputStream
import java.util.*

@SuppressLint("MissingPermission")
class BluetoothController(private val context: Context): IBluetoothController {

    private val bluetoothManager by lazy { context.getSystemService(BluetoothManager::class.java) }
    private val bluetoothAdapter by lazy { bluetoothManager?.adapter }

//    private var dataTransferService: BluetoothDataTransferService? = null

    private val _scannedDevices = MutableStateFlow<List<BluetoothDeviceDomain>>(emptyList())
    override val scannedDevices: StateFlow<List<BluetoothDeviceDomain>> get() = _scannedDevices.asStateFlow()

    private val _isConnected = MutableStateFlow(false)
    override val isConnected: StateFlow<Boolean> get() = _isConnected.asStateFlow()

    private val _pairedDevices = MutableStateFlow<List<BluetoothDeviceDomain>>(emptyList())
    override val pairedDevices: StateFlow<List<BluetoothDeviceDomain>> get() = _pairedDevices.asStateFlow()

    private val _errors = MutableSharedFlow<String>()
    override val errors: SharedFlow<String> get() = _errors.asSharedFlow()


    private val foundDeviceReceiver = BluetoothDeviceReceiver { device ->
        _scannedDevices.update { devices ->
            val newDevice = device.toBluetoothDeviceDomain()
            if (newDevice in devices) devices else devices + newDevice
        }
    }

    private val bluetoothStateReceiver = BluetoothStateReceiver { isConnected, bluetoothDevice ->
        if (bluetoothAdapter?.bondedDevices?.contains(bluetoothDevice) == true) {
            _isConnected.update { isConnected }
        } else {
            CoroutineScope(Dispatchers.IO).launch {
                _errors.tryEmit("Can't cnnect to a non-paired device")
            }
        }
    }

    private var currentServerSocket: BluetoothServerSocket? = null
    private var currentClientSocket: BluetoothSocket? = null


    init {
        updatePairedDevices()
        context.registerReceiver(
            bluetoothStateReceiver,
            IntentFilter().apply {
                addAction(BluetoothAdapter.ACTION_CONNECTION_STATE_CHANGED)
                addAction(android.bluetooth.BluetoothDevice.ACTION_ACL_CONNECTED)
                addAction(android.bluetooth.BluetoothDevice.ACTION_ACL_DISCONNECTED)
            }
        )
    }


    // override functions
    override fun startDiscovery() {
        if (!hasPermission(Manifest.permission.BLUETOOTH_SCAN)) {
            return
        }

        context.registerReceiver(
            foundDeviceReceiver,
            IntentFilter(android.bluetooth.BluetoothDevice.ACTION_FOUND)
        )


        updatePairedDevices()
        bluetoothAdapter?.startDiscovery()
    }

    override fun stopDiscovery() {
        if (!hasPermission(Manifest.permission.BLUETOOTH_SCAN)) {
            return
        }

        bluetoothAdapter?.cancelDiscovery()
    }

    override fun startBluetoothServer(): Flow<ConnectionResult> {
        TODO()
//        return flow {
//            if(!hasPermission(Manifest.permission.BLUETOOTH_CONNECT)) {
//                throw SecurityException("No BLUETOOTH_CONNECT permission")
//            }
//
//            currentServerSocket = bluetoothAdapter?.listenUsingRfcommWithServiceRecord(
//                "chat_service",
//                UUID.fromString(SERVICE_UUID)
//            )
//
//            var shouldLoop = true
//            while(shouldLoop) {
//                currentClientSocket = try {
//                    currentServerSocket?.accept()
//                } catch(e: IOException) {
//                    shouldLoop = false
//                    null
//                }
//                emit(ConnectionResult.ConnectionEstablished)
//                currentClientSocket?.let {
//                    currentServerSocket?.close()
//                    val service = BluetoothDataTransferService(it)
//                    dataTransferService = service
//
//                    emitAll(
//                        service
//                            .listenForIncomingMessages()
//                            .map {
//                                ConnectionResult.TransferSucceeded(it)
//                            }
//                    )
//                }
//            }
//        }.onCompletion {
//            closeConnection()
//        }.flowOn(Dispatchers.IO)
    }

    override fun connectToDevice(device: BluetoothDeviceDomain){
        currentClientSocket = bluetoothAdapter
            ?.getRemoteDevice(device.address)
            ?.createRfcommSocketToServiceRecord((
                    UUID.fromString(SERVICE_UUID)
                    ))
        stopDiscovery()

//        Log.d("Success", "after socket")


        // if bluetooth socket is not null, connect with device
        // if bluetooth socket is null, close socket
        currentClientSocket?.let { socket ->
            try {
                socket.connect()
                Log.d("Success", "Socket connected")

            } catch(e: IOException) {
                socket.close()
                Log.d("Success", e.message.toString())
            }
        }

    }


    override fun disconnectFromBluetoothDevice(device: BluetoothDeviceDomain) {}
    override fun trySendMessage(message: String, ) {
        var outStream : OutputStream? = null

        try {
            outStream = currentClientSocket?.outputStream
        } catch (e: IOException) {
            Log.d("Success", "Bug BEFORE Sending stuff", e)
            // error occur, probably connection error
        }
        // converts message (location) to byte array
        val msgBuffer = message.toByteArray()

         try {
            // sends location to arduino device
            outStream?.write(msgBuffer)
            Log.d("Success", "Location send")


        } catch (e: IOException) {
            Log.d("Success", "Bug while sending stuff", e)
            // error occur, probably socket error

        }


//        val outStream: OutputStream?
//
//        // converts message (location) to byte array
//        Log.d("Success", "Before Sending message")
//
//
//        try {
//            outStream = currentClientSocket?.outputStream
//            outStream!!.write(message.toByteArray())
//
//
//            Log.d("Success", "Location send")
//
//        } catch (e: IOException) {
//            Log.d("Success", "Bug while sending stuff", e)
//            // error occur, probably socket error
//        }
//        // message send successfully
    }


    override fun closeConnection() {
        currentClientSocket?.close()
        currentServerSocket?.close()
        currentClientSocket = null
        currentServerSocket = null
    }

    override fun release() {
        context.unregisterReceiver(foundDeviceReceiver)
        context.unregisterReceiver(bluetoothStateReceiver)
        closeConnection()
    }

    private fun updatePairedDevices() {
        if(!hasPermission(Manifest.permission.BLUETOOTH_CONNECT)) {
            return
        }
        bluetoothAdapter
            ?.bondedDevices
            ?.map { it.toBluetoothDeviceDomain() }
            ?.also { devices ->
                _pairedDevices.update { devices }
            }
    }

    private fun hasPermission(permission: String): Boolean {
        return context.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED
    }

    companion object{
        const val SERVICE_UUID = "00001101-0000-1000-8000-00805f9b34fb"
        const val MAC_ADDRESS = "E4:5F:01:5C:6C:CO"
    }
}