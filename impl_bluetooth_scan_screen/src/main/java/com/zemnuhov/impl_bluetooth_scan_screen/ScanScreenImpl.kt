package com.zemnuhov.impl_bluetooth_scan_screen

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.neurotech.core_bluetooth_comunication_api.ConnectionState
import com.zemnuhov.api_bluetooth_scan.Device
import com.zemnuhov.api_bluetooth_scan.Devices
import com.zemnuhov.bluetooth_scan_screen_api.ScanScreen
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class ScanScreenImpl @Inject constructor(@ApplicationContext context: Context) : ScanScreen {

    @Inject lateinit var viewModelFactory: ScanScreenViewModel.Factory
    lateinit var viewModel: ScanScreenViewModel

    init {
        ScanScreenComponent.get(context).inject(this)
    }

    @Composable
    override fun DrawScreen(navController: NavController) {
        viewModel = viewModel(factory = viewModelFactory)
        val devices = viewModel.bleDevices.collectAsState(initial = Devices(listOf()))
        val connectionState = viewModel.connectionState.collectAsState(initial = ConnectionState.DISCONNECTED)

        if(connectionState.value == ConnectionState.CONNECTED){
            navController.navigate("main")
        }
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
                CoroutineScope(Dispatchers.IO).launch { viewModel.startScan() }
            }, modifier = Modifier
                .fillMaxWidth()
                .weight(0.05F),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0,187,89))
            ) {
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
            .background(color = Color(219, 219, 219))
            .clickable {
                viewModel.connectToDevice(device)
            }
        ) {
            Icon(imageVector = Icons.Default.Star, contentDescription = "Device Icon", modifier = Modifier
                .size(50.dp)
                .align(Alignment.CenterVertically)
                .padding(start = 16.dp))
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
