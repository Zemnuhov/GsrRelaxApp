package com.example.impl_main_screen

import android.content.Context
import com.neurotech.core_bluetooth_comunication_api.BluetoothConnection
import com.neurotech.core_bluetooth_comunication_api.BluetoothData
import dagger.Component
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent
import kotlin.properties.Delegates.notNull

@Component(dependencies = [MainScreenDependencies::class])
interface MainScreenComponent {
    fun inject(screen: MainScreenImpl)

    @Component.Builder
    interface Builder{
        fun provideDependencies(mainScreenDependencies: MainScreenDependencies):Builder
        fun build(): MainScreenComponent
    }

    companion object{
        private var component: MainScreenComponent? = null

        internal fun get(context: Context): MainScreenComponent{
            if(component == null){
                component = DaggerMainScreenComponent
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
interface MainScreenDependencies {
    fun bluetoothConnection(): BluetoothConnection
    fun bluetoothData(): BluetoothData
}

interface ScanScreenDependenciesProvider {
    val dependencies: MainScreenDependencies
    companion object : ScanScreenDependenciesProvider by ScanDependenciesStore
}

object ScanDependenciesStore : ScanScreenDependenciesProvider {
    override var dependencies: MainScreenDependencies by notNull()
}