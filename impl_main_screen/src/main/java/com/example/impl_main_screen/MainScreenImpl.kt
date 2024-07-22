package com.example.impl_main_screen

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.api_main_screen.MainScreen
import com.neurotech.core_bluetooth_comunication_api.model.Phase
import com.neurotech.core_bluetooth_comunication_api.model.Tonic
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.Date
import javax.inject.Inject

class MainScreenImpl @Inject constructor(@ApplicationContext context: Context) : MainScreen {

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
        Column(
            modifier = Modifier.verticalScroll(rememberScrollState())
        ) {
            PhaseGraph()
            TonicGraph()
            Info()
            Button(
                onClick = { viewModel.beginSession() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp)
                    .shadow(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0,187,89))
            ) {
                Text(text = "Начать сессию")
            }
        }

    }


    @Composable
    fun PhaseGraph() {
        Text(
            text = "Фазика",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(16.dp)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .padding(16.dp)
                .shadow(16.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Color.White)

        ) {
            val phaseValue = viewModel.phaseData.collectAsState(initial = Phase(0.0, Date()))
            val graph = Graph()
            graph.LineChart(
                listOf(), setting = Graph.ChartSetting(
                    threshold = 3F, minY = -30F, maxY = 30F, minPointsInScreen = 500
                ), modifier = Modifier.fillMaxSize()
            )
            graph.addPoint(Point(phaseValue.value.time.time, phaseValue.value.value.toFloat()))
        }
    }

    @Composable
    fun TonicGraph() {
        Text(
            text = "Тоника",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(16.dp)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .padding(16.dp)
                .shadow(16.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Color.White)
        ) {
            val tonicValue = viewModel.tonicData.collectAsState(initial = Tonic(0, Date()))
            val graph = Graph()
            graph.LineChart(
                listOf(), setting = Graph.ChartSetting(
                    minY = -30F, maxY = 30F, minPointsInScreen = 500
                ), modifier = Modifier.fillMaxSize()
            )
            graph.setYDiapason(
                tonicValue.value.value.toFloat() - 100F, tonicValue.value.value.toFloat() + 100F
            )
            graph.addPoint(Point(tonicValue.value.time.time, tonicValue.value.value.toFloat()))
        }
    }

    @Composable
    fun Info() {
        val tonicValue = viewModel.tonicData.collectAsState(initial = Tonic(0, Date()))
        val peaksCount = viewModel.peaksCount.collectAsState(initial = 0)
        Text(
            text = "Текущее значение тоники: ${tonicValue.value.value}",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(16.dp)
        )
        Text(
            text = "Пиков за сессию: ${peaksCount.value}",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(16.dp)
        )
        Text(
            text = "Вы опустили тонику на: ${if (viewModel.beginTonic - tonicValue.value.value > 0) (viewModel.beginTonic - tonicValue.value.value) else 0}",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(16.dp)
        )
    }
}