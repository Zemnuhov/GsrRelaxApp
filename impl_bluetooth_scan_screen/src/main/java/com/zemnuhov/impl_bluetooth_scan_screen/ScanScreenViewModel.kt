package com.zemnuhov.impl_bluetooth_scan_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.neurotech.core_bluetooth_comunication_api.BluetoothConnection
import com.zemnuhov.api_bluetooth_scan.BluetoothScan
import com.zemnuhov.api_bluetooth_scan.Device
import kotlinx.coroutines.launch
import javax.inject.Inject


class ScanScreenViewModel(
    private val bluetoothScan: BluetoothScan,
    private val bluetoothConnection: BluetoothConnection
) : ViewModel() {

    val bleDevices = bluetoothScan.getDevicesFlow()
    val connectionState = bluetoothConnection.getConnectionStateFlow()


    fun startScan() {
        viewModelScope.launch {
            bluetoothScan.startScan()
        }
    }

    fun connectToDevice(device: Device){
        viewModelScope.launch {
            bluetoothConnection.connectionToPeripheral(device.mac)
        }
    }

    @Suppress("UNCHECKED_CAST")
    class Factory @Inject constructor(
        private var bluetoothScan: BluetoothScan,
        private var bluetoothConnection : BluetoothConnection
    ) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            require(modelClass == ScanScreenViewModel::class.java)
            return ScanScreenViewModel(
                bluetoothScan = bluetoothScan,
                bluetoothConnection = bluetoothConnection
            ) as T
        }
    }
}

