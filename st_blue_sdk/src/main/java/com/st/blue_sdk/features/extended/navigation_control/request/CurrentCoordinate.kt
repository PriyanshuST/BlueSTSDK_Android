package com.st.blue_sdk.features.extended.navigation_control.request

import com.st.blue_sdk.features.FeatureCommand
import com.st.blue_sdk.features.extended.navigation_control.NavigationControl

class CurrentCoordinate(
    feature : NavigationControl,
    val action : UByte,
    val xCoordinate: Int,
    val yCoordinate: Int,
    val theta : Short,
    val interval: Short,
    val res : Long
) : FeatureCommand(feature = feature, commandId = NavigationControl.CURRENT_COORDINATE)