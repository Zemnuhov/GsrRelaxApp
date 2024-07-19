package com.example.impl_main_screen

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.api_main_screen.MainScreen
import com.neurotech.core_bluetooth_comunication_api.model.Phase
import com.patrykandpatrick.vico.compose.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberBottomAxis
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberStartAxis
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLineCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.cartesian.data.lineSeries
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.Date
import javax.inject.Inject

class MainScreenImpl @Inject constructor(@ApplicationContext context: Context): MainScreen {

    @Inject
    lateinit var viewModelFactory: MainScreenViewModel.Factory
    lateinit var viewModel: MainScreenViewModel

    init {
        MainScreenComponent.get(context)
    }

    @SuppressLint("MutableCollectionMutableState")
    @Composable
    override fun DrawScreen() {
        viewModel = viewModel(factory = viewModelFactory)
        Box(modifier = Modifier.fillMaxSize()) {
            val phaseValue = viewModel.phaseData.collectAsState(initial = Phase(0.0, Date()))
            val phaseData = remember { mutableStateOf(mutableListOf<Phase>())}
            phaseData.value.add(phaseValue.value)
            Log.e("Values", phaseValue.value.toString())
            val modelProducer = remember { CartesianChartModelProducer() }
            LaunchedEffect(Unit) { modelProducer.runTransaction { lineSeries { series(phaseData.value.map { phase -> phase.value }) } } }
            CartesianChartHost(
                rememberCartesianChart(
                    rememberLineCartesianLayer(),
                    startAxis = rememberStartAxis(),
                    bottomAxis = rememberBottomAxis(),
                ),
                modelProducer,
            )
        }
    }
}