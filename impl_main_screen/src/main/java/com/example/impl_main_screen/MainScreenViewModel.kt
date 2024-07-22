package com.example.impl_main_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.neurotech.core_bluetooth_comunication_api.BluetoothConnection
import com.neurotech.core_bluetooth_comunication_api.BluetoothData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainScreenViewModel(
    private val bluetoothConnection: BluetoothConnection,
    private val bluetoothData: BluetoothData
) : ViewModel() {

    val tonicData = bluetoothData.getTonicValueFlow()
    val phaseData = bluetoothData.getPhaseValueFlow()
    val connectionState = bluetoothConnection.getConnectionStateFlow()

    var peaksCount = MutableStateFlow(0)
    var isPeaks = false
    var beginTonic = 0

    init {
        viewModelScope.launch {
            phaseData.collect{
                if(it.value>3 && !isPeaks){
                    peaksCount.emit(peaksCount.value+1)
                    isPeaks = true
                }
                if(it.value < 3 && isPeaks){
                    isPeaks = false
                }
            }
        }
        viewModelScope.launch {
            beginTonic = tonicData.first().value
        }
    }

    fun beginSession(){
        viewModelScope.launch {
            beginTonic = tonicData.first().value
        }
        viewModelScope.launch {
            peaksCount.emit(0)
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
