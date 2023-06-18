package com.example.hachathon_2023_inifia.presentation.components

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.hachathon_2023_inifia.presentation.components.screens.HomeScreen
import com.example.hachathon_2023_inifia.presentation.components.screens.LoadingScreen
import com.example.hachathon_2023_inifia.presentation.components.screens.SplashScreen
import com.example.hachathon_2023_inifia.presentation.components.screens.UploadFileScreen
import com.example.hachathon_2023_inifia.presentation.viewmodels.BluetoothViewModel
import com.example.hachathon_2023_inifia.utils.Constants
import com.example.hachathon_2023_inifia.utils.toasts.CustomToasts
import com.shashank.sony.fancytoastlib.FancyToast

@Composable
fun Navigation(context: Context) {
    val navController = rememberNavController()
    val customToasts = CustomToasts()

    val viewModel = hiltViewModel<BluetoothViewModel>()
    val state by viewModel.state.collectAsState()

    fun liveSocket() {
        viewModel.connectToDevice(state.devices.first())
    }

    NavHost(navController = navController, startDestination = "splash_screen"){
        composable(route = Constants.splash_route_name){
            SplashScreen(navController)
        }
        composable(route = Constants.upload_file_route_name){

            UploadFileScreen(
                sendData = viewModel::sendMessage,
                makeLiveAgain = { liveSocket() }
            )
        }

        composable(route = Constants.home_route_name){

            LaunchedEffect(key1 = state.errorOccur) {
                if(state.isConnected) {
                    customToasts.customToast(context, Constants.not_connect_devices, FancyToast.DEFAULT)
                }
            }

            viewModel.startScan()

            when{
                // if devices are connecting then show LoadingScreen()
                state.isConnecting -> {
                    LoadingScreen()
                }
                // if devices are connected shows ChatScreen
                state.isConnected -> {
                    Log.d("Success", "device ${state.devices.first()}")
                    viewModel.connectToDevice(state.devices.first())

                    UploadFileScreen(
                        sendData = viewModel::sendMessage,
                        makeLiveAgain = { liveSocket() }
                    )
                }
                state.errorOccur -> {
                    Toast.makeText(context, Constants.not_connect_devices, Toast.LENGTH_LONG).show()
                    HomeScreen(
                        state = state,
                        onStartScan = viewModel::startScan,
                        onStopScan = viewModel::stopScan,
                        connectToDevice = viewModel::connectToDevice,
                        context = context
                    )
                }
                // in else state shows device screen
                else -> {
                    HomeScreen(
                        state = state,
                        onStartScan = viewModel::startScan,
                        onStopScan = viewModel::stopScan,
                        connectToDevice = viewModel::connectToDevice,
                        context = context
                    )
                }
            }
        }

    }


}