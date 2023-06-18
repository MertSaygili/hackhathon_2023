package com.example.hachathon_2023_inifia.presentation.components.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Shapes
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@Composable
fun CustomSmallButton(text: String, event: () -> Unit) {
    // small button for baglan, sunucu are
    // responsive
    Button(
        modifier = Modifier.padding(4.dp),
//        colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = colorId)),
//        shape = Shapes.small,
        contentPadding = PaddingValues(0.dp),
        onClick = event,
    ) {
        Text("sss")
    }
}