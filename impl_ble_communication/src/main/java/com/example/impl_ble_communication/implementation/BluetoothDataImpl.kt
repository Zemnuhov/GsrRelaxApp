package com.neurotech.core_bluetooth_comunication_impl.implementation

import com.neurotech.core_bluetooth_comunication_api.BluetoothData
import com.neurotech.core_bluetooth_comunication_api.model.Phase
import com.neurotech.core_bluetooth_comunication_api.model.Tonic
import com.example.impl_ble_communication.AppBluetoothManager

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.*
import javax.inject.Inject

class BluetoothDataImpl @Inject constructor(val bleManager: AppBluetoothManager): BluetoothData {
    override fun getTonicValueFlow(): Flow<Tonic> =
        bleManager.tonicValueFlow.map { Tonic(it, Date()) }


    override fun getPhaseValueFlow(): Flow<Phase> =
        bleManager.phaseValueFlow.map { Phase(it,Date()) }
}