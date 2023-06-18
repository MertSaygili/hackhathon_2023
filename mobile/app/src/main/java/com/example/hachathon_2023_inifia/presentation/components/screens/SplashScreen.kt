package com.example.hachathon_2023_inifia.presentation.components.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.example.hachathon_2023_inifia.R
import com.example.hachathon_2023_inifia.utils.Constants
import kotlinx.coroutines.delay


@Composable
fun SplashScreen(navController: NavController) {
    // waits 3 seconds and push navigator to home screen
    LaunchedEffect(key1 = true){
        delay(3000L)
        navController.navigate(Constants.home_route_name){
            // after pop up, closes app
            popUpTo(0)
        }
    }

    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
        Image(painter = painterResource(id = R.drawable.infinia_logo), contentDescription = "logo", contentScale = ContentScale.Crop)
    }
}