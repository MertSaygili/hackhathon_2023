package com.example.hachathon_2023_inifia.presentation.components.component


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun CustomFloatingActionButton(text: String, icon: ImageVector, iconContent: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier.padding(horizontal = 12.dp)
    ){
        FloatingActionButton(
            onClick = onClick
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 16.dp), verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text, color = Color.White, fontSize = 16.sp)
                Spacer(modifier = Modifier.padding(horizontal = 2.dp))
                Icon(
                    imageVector = icon,
                    contentDescription = iconContent,
                )
            }
        }
    }

}