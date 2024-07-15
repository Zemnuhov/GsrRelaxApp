package com.zemnuhov.impl_bluetooth_scan_screen

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
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
                        DrawCard(device = device)
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
            .padding(8.dp)
            .shadow(8.dp, shape = RoundedCornerShape(16.dp))
            .clip(RoundedCornerShape(16.dp))
            .background(color = Color(219,219,219))

        ) {
            Icon(imageVector = Icons.Default.Star, contentDescription = "Device Icon", modifier = Modifier.size(50.dp).align(Alignment.CenterVertically).padding(start = 16.dp))
            Column(modifier = Modifier.padding(8.dp)) {
                Row(modifier = Modifier.padding(8.dp)) {
                    Text(text = "Имя устройства:", fontWeight = FontWeight.Bold)
                    Text(text = device.name)
                }
                Row(modifier = Modifier.padding(8.dp)) {
                    Text(text = "MAC:", fontWeight = FontWeight.Bold)
                    Text(text = device.mac)
                }
                
            }
        }
    }
}
