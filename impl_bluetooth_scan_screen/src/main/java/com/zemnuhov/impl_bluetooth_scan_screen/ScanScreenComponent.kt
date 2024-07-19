package com.zemnuhov.impl_bluetooth_scan_screen

import android.content.Context
import com.neurotech.core_bluetooth_comunication_api.BluetoothConnection
import com.zemnuhov.api_bluetooth_scan.BluetoothScan
import dagger.Component
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent
import kotlin.properties.Delegates.notNull


@Component(dependencies = [ScanScreenDependencies::class])
interface ScanScreenComponent {

    fun inject(screen: ScanScreenImpl)

    @Component.Builder
    interface Builder{
        fun provideDependencies(scanScreenDependencies: ScanScreenDependencies):Builder
        fun build(): ScanScreenComponent
    }

    companion object{
        private var component: ScanScreenComponent? = null

        internal fun get(context: Context): ScanScreenComponent{
            if(component == null){
                component = DaggerScanScreenComponent
                    .builder()
                    .provideDependencies(EntryPointAccessors.fromApplication(context))
                    .build()
            }
            return component!!
        }

        internal fun clear(){
            component = null
        }
    }
}

@EntryPoint
@InstallIn(SingletonComponent::class)
interface ScanScreenDependencies {
    fun bluetoothScan(): BluetoothScan
    fun bluetoothConnection(): BluetoothConnection
}

interface ScanScreenDependenciesProvider {
    val dependencies: ScanScreenDependencies
    companion object : ScanScreenDependenciesProvider by ScanDependenciesStore
}

object ScanDependenciesStore : ScanScreenDependenciesProvider {
    override var dependencies: ScanScreenDependencies by notNull()
}