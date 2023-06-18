package com.example.hachathon_2023_inifia.di

import android.content.Context
import com.example.hachathon_2023_inifia.controller.BluetoothController
import com.example.hachathon_2023_inifia.domain.IBluetoothController
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideBluetoothController(@ApplicationContext context: Context): IBluetoothController {
        return BluetoothController(context)
    }
}

