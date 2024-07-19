package com.example.impl_main_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.neurotech.core_bluetooth_comunication_api.BluetoothConnection
import com.neurotech.core_bluetooth_comunication_api.BluetoothData
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainScreenViewModel(
    private val bluetoothConnection: BluetoothConnection,
    private val bluetoothData: BluetoothData
) : ViewModel() {

    val tonicData = bluetoothData.getTonicValueFlow()
    val phaseData = bluetoothData.getPhaseValueFlow()
    val connectionState = bluetoothConnection.getConnectionStateFlow()

    init {
        viewModelScope.launch {
            phaseData.collect{
                Log.e("TEST", it.toString())
            }
        }
    }


    @Suppress("UNCHECKED_CAST")
    class Factory @Inject constructor(
        private var bluetoothConnection: BluetoothConnection,
        private var bluetoothData: BluetoothData
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MainScreenViewModel(
                bluetoothConnection,
                bluetoothData
            ) as T
        }
    }
}
