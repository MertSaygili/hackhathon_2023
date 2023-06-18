package com.example.hachathon_2023_inifia.presentation.components.component

import android.content.Context
import android.content.Intent
import android.provider.Settings
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.example.hachathon_2023_inifia.R

@Composable
fun CustomAppbar(context: Context?, title: String, needAction: Boolean, goBack: Boolean, goBackFunction: () -> Unit) {
    // appbar that used in app for every page
    TopAppBar(
        title  = { Text(text = title, color = colorResource(id = R.color.white)) },
        backgroundColor = colorResource(id = R.color.success),
        elevation = 8.dp,
        navigationIcon = {
            if(goBack) {
                IconButton(onClick = { goBackFunction() }) {
                    Icon(Icons.Filled.ArrowBack, "Back", tint = colorResource(id = R.color.white))
                }
            }
        },
        actions = {
            if (needAction) {
                IconButton(
                    onClick = { context?.startActivity(Intent(Settings.ACTION_BLUETOOTH_SETTINGS)) }
                ) {
                    Icon(Icons.Filled.Settings, "Settings", tint = colorResource(id = R.color.white))
                }
            }
        }
    )
}