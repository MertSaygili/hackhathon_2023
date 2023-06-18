package com.example.hachathon_2023_inifia.presentation.components.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CustomPlaylistCard(imageName: String, imageAddress: String) {
    val paddingModifier  = Modifier.padding(10.dp)

    Card(
        elevation = 10.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp, horizontal = 10.dp)
            .clickable {

            }

    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(text = imageName, modifier = paddingModifier)
            Text(text = imageAddress, modifier = paddingModifier)

        }
    }
}
