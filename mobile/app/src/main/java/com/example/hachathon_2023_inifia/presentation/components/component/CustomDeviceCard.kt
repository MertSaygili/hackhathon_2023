package com.example.hachathon_2023_inifia.presentation.components.component

import android.content.Context
import android.text.style.ClickableSpan
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CustomDeviceCard (deviceName: String, deviceMacAddress: String, function: () -> Unit) {
    val paddingModifier  = Modifier.padding(10.dp)

    Card(
        elevation = 10.dp,
        modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp, horizontal = 10.dp).clickable{
            function()
        }

    ) {
        Text(text = "$deviceName - $deviceMacAddress",
            modifier = paddingModifier)
    }
}