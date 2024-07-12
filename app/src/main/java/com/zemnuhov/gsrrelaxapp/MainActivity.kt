package com.zemnuhov.gsrrelaxapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.zemnuhov.bluetooth_scan_screen_api.ScanScreen
import com.zemnuhov.gsrrelaxapp.ui.theme.GsrRelaxAppTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    @Inject
    lateinit var scanScreen: ScanScreen


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
    fun Greeting( modifier: Modifier = Modifier) {
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = "scan") {
            composable("scan") { scanScreen.DrawScreen() }
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

