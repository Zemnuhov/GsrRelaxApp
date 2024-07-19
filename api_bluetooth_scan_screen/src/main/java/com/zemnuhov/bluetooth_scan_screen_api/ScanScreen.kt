package com.zemnuhov.bluetooth_scan_screen_api

import androidx.compose.runtime.Composable
import androidx.navigation.NavController

interface ScanScreen {
    @Composable fun DrawScreen(navController: NavController)
}