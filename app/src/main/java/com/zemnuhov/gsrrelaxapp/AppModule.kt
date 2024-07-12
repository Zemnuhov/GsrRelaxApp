package com.zemnuhov.gsrrelaxapp

import android.content.Context
import com.zemnuhov.api_bluetooth_scan.BluetoothScan
import com.zemnuhov.bluetooth_scan_screen_api.ScanScreen
import com.zemnuhov.impl_bluetooth_scan.BluetoothScanImpl
import com.zemnuhov.impl_bluetooth_scan_screen.ScanScreenImpl
import dagger.Binds
import dagger.Component.Builder
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
}