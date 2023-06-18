package com.example.hachathon_2023_inifia.presentation.components.screens

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Handler
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.hachathon_2023_inifia.controller.mapper.toByteArray
import com.example.hachathon_2023_inifia.presentation.components.component.CustomAppbar
import com.example.hachathon_2023_inifia.presentation.components.component.CustomFloatingActionButton
import com.example.hachathon_2023_inifia.utils.Constants
import java.io.ByteArrayOutputStream
import java.nio.ByteBuffer


@SuppressLint("NewApi")
@Composable
fun UploadFileScreen (
    sendData: (message: String) -> Unit,
    makeLiveAgain : () -> Unit
) {
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    var selectedVideoUri by remember { mutableStateOf<Uri?>(null) }
    val stream = ByteArrayOutputStream()
    var byteArray: ByteArray? = null
    val context = LocalContext.current
    val bitmap = remember{ mutableStateOf<Bitmap?>(null)}
    var byteArrayList : List<ByteArray>  = emptyList()
    var x : String = ""

    val imageLauncher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) {
        selectedImageUri = it
    }

    val videoLauncher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) {
        selectedVideoUri = it
    }


    Scaffold(
        topBar = { CustomAppbar(
            context = context,
            title = Constants.playlist_title,
            needAction = true,
            goBack = false,
            goBackFunction = {}
        ) }
    ) { contentPadding ->
        Box(modifier = Modifier.padding(contentPadding)) {
            Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceBetween) {
                selectedImageUri?.let {
                    if(Build.VERSION.SDK_INT < 20) {
                        bitmap.value = MediaStore.Images.Media.getBitmap(context.contentResolver, it)
                    }
                    else{
                        val source = ImageDecoder.createSource(context.contentResolver, it)
                        bitmap.value= ImageDecoder.decodeBitmap(source)
                    }

                    bitmap.value?.let { btm ->
                        bitmap.value!!.compress(Bitmap.CompressFormat.JPEG, 80, stream)
                        byteArray = stream.toByteArray()
                        Log.d("Success", x.toByteArray().toString())

                        Image(
                            bitmap = btm.asImageBitmap(),
                            contentDescription = null,
                            modifier = Modifier
                                .size(400.dp)
                                .padding(20.dp)
                        )

//                        if(bitmap.value != null && byteArray != null ) {
//                            byteArrayList = byteArrayList + byteArray!!.joinToString("") { "%02x".format(it) }.toByteArray()
//                        }
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))

                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 6.dp), horizontalArrangement = Arrangement.SpaceAround) {
                    CustomFloatingActionButton("photo", Icons.Rounded.Search, "take photo") {
                        imageLauncher.launch("image/*")
                    }
                    CustomFloatingActionButton("video", Icons.Default.Add, "print byte array") {
                        Log.d("Success", byteArray!!.joinToString("") { "%02x".format(it) })
                    }

                    CustomFloatingActionButton("", Icons.Default.Send, "send") {
//                        x = byteArray!!.joinToString("") { "%02x".format(it) }
                        x = Base64.encodeToString(byteArray, Base64.DEFAULT);
                        makeLiveAgain()

                        Handler().postDelayed({
//                             sendData("XXX" + "$$$$$"  + "YYYY" + "$$$$$" + "data:application/octet-stream;base64," + x)
                        }, 2000)
                        sendData("XXX" + "$$$$$"  + "YYYY" + "$$$$$" + "data:application/octet-stream;base64,iVBORw0KGgoAAAANSUhEUgAAAgAAAAIAAQMAAADOtka5AAAABGdBTUEAALGPC/xhBQAAACBjSFJNAAB6JgAAgIQAAPoAAACA6AAAdTAAAOpgAAA6mAAAF3CculE8AAAABlBMVEX/AAD///9BHTQRAAAAAWJLR0QB/wIt3gAAAAd0SU1FB+YFFBEZLkFgcBcAAAA2SURBVHja7cEBAQAAAIIg/69uSEABAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAHwbggAAAWN1UKQAAAAldEVYdGRhdGU6Y3JlYXRlADIwMjItMDUtMjBUMTc6MjU6NDYrMDA6MDBx2RxkAAAAJXRFWHRkYXRlOm1vZGlmeQAyMDIyLTA1LTIwVDE3OjI1OjQ2KzAwOjAwAISk2AAAAABJRU5ErkJggg==")


//                        Log.d("Success", byteArray!!.joinToString("") { "%02x".format(it) })
                    }
                }

            }
        }
    }
}