package com.neurotech.core_bluetooth_comunication_api

import com.neurotech.core_bluetooth_comunication_api.model.Phase
import com.neurotech.core_bluetooth_comunication_api.model.Tonic
import kotlinx.coroutines.flow.Flow

interface BluetoothData {
    fun getTonicValueFlow(): Flow<Tonic>
    fun getPhaseValueFlow(): Flow<Phase>
}