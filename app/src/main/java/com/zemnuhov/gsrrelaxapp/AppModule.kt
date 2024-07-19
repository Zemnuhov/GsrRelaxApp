package com.zemnuhov.gsrrelaxapp

import com.example.api_main_screen.MainScreen
import com.example.impl_ble_communication.implementation.BluetoothConnectionImpl
import com.example.impl_main_screen.MainScreenImpl
import com.neurotech.core_bluetooth_comunication_api.BluetoothConnection
import com.neurotech.core_bluetooth_comunication_api.BluetoothData
import com.neurotech.core_bluetooth_comunication_impl.implementation.BluetoothDataImpl
import com.zemnuhov.api_bluetooth_scan.BluetoothScan
import com.zemnuhov.bluetooth_scan_screen_api.ScanScreen
import com.zemnuhov.impl_bluetooth_scan.BluetoothScanImpl
import com.zemnuhov.impl_bluetooth_scan_screen.ScanScreenImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {
    @Binds
    abstract fun provideBluetoothScan(bluetoothScan: BluetoothScanImpl): BluetoothScan

    @Binds
    abstract fun provideBluetoothScanScreen(screen: ScanScreenImpl): ScanScreen

    @Binds
    abstract fun provideMainScreen(screen: MainScreenImpl): MainScreen

    @Binds
    abstract fun provideBluetoothConnection(bluetoothConnection: BluetoothConnectionImpl): BluetoothConnection

    @Binds
    abstract fun provideBluetoothData(bluetoothConnection: BluetoothDataImpl): BluetoothData
}