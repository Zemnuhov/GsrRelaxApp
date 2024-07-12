package com.zemnuhov.impl_bluetooth_scan_screen

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import com.zemnuhov.api_bluetooth_scan.BluetoothScan
import com.zemnuhov.api_bluetooth_scan.Device
import com.zemnuhov.api_bluetooth_scan.Devices
import com.zemnuhov.bluetooth_scan_screen_api.ScanScreen
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class ScanScreenImpl @Inject constructor(@ApplicationContext context: Context) : ScanScreen {

    @Inject
    lateinit var bluetoothScan: BluetoothScan

    init {
        ScanScreenComponent.get(context).inject(this)
    }

    @Composable
    override fun DrawScreen() {
        val devices = bluetoothScan.getDevicesFlow().collectAsState(initial = Devices(listOf()))

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            LazyColumn(modifier = Modifier
                .fillMaxWidth()
                .weight(0.95F)) {
                for (device in devices.value.items){
                    item { 
                        Text(text = device.name)
                    }
                }
            }
            Button(onClick = {
                CoroutineScope(Dispatchers.IO).launch { bluetoothScan.startScan() }
            }, modifier = Modifier
                .fillMaxWidth()
                .weight(0.05F)) {
                Text("Начать поиск Bluetooth устройств")
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }

    @Composable
    fun DrawCard(device: Device){
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)) {

        }
    }
}
