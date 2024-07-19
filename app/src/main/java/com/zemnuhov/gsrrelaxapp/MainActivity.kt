package com.zemnuhov.gsrrelaxapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.api_main_screen.MainScreen
import com.zemnuhov.bluetooth_scan_screen_api.ScanScreen
import com.zemnuhov.gsrrelaxapp.ui.theme.GsrRelaxAppTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    @Inject
    lateinit var scanScreen: ScanScreen

    @Inject
    lateinit var mainScreen: MainScreen


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GsrRelaxAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }

    @Composable
    fun Greeting(modifier: Modifier = Modifier) {
        val navController = rememberNavController()
        Column(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.1F)
                    .shadow(16.dp)
                    .background(color = Color.LightGray),
                contentAlignment = Alignment.BottomStart
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.app_icon),
                    contentDescription = "Icon",
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(start = 16.dp, top = 32.dp)

                )
            }
            NavHost(navController = navController, startDestination = "scan") {
                composable("scan") { scanScreen.DrawScreen(navController) }
                composable("main") { mainScreen.DrawScreen() }
            }
        }

    }

    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        GsrRelaxAppTheme {
            Greeting()
        }
    }
}

