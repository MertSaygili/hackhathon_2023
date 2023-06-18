package com.example.hachathon_2023_inifia.utils.toasts

import android.content.Context
import com.shashank.sony.fancytoastlib.FancyToast

class CustomToasts () {
    fun customToast(context: Context, text: String, type: Int) {
        FancyToast.makeText(context, text, FancyToast.LENGTH_LONG, type, false).show()
    }
}