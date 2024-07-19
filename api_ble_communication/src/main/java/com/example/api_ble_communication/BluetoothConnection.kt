package com.neurotech.core_bluetooth_comunication_api

import kotlinx.coroutines.flow.Flow

interface BluetoothConnection {
    suspend fun connectionToPeripheral(deviceMac: String)
    fun getConnectionStateFlow(): Flow<ConnectionState>
    fun getConnectionState():ConnectionState
    suspend fun disconnectDevice()
}