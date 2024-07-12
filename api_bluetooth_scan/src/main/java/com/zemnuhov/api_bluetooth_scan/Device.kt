package com.zemnuhov.api_bluetooth_scan

data class Devices(
    val items: List<Device>
)

data class Device(
    val name: String,
    val mac: String
)