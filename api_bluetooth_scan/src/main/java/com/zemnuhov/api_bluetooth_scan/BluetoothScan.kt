package com.zemnuhov.api_bluetooth_scan

import kotlinx.coroutines.flow.Flow

interface BluetoothScan {
    fun getDevicesFlow(): Flow<Devices>
    fun getScanState(): Flow<Boolean>
    suspend fun startScan()
    suspend fun stopScan()
}