package com.st.blue_sdk.features.extended.navigation_control.request

import com.st.blue_sdk.features.FeatureCommand
import com.st.blue_sdk.features.extended.navigation_control.NavigationControl

class CoordinateOrigin (
    feature: NavigationControl,
    val action: UByte,
    val command : UByte,
    val xCoordinate : Int,
    val yCoordinate : Int,
    val theta : UShort,
    val res : Long
) : FeatureCommand(feature = feature , commandId = NavigationControl.COORDINATE_ORIGIN)