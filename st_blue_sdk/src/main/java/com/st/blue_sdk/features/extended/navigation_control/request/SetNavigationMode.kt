package com.st.blue_sdk.features.extended.navigation_control.request

import com.st.blue_sdk.features.FeatureCommand
import com.st.blue_sdk.features.extended.navigation_control.NavigationControl

class SetNavigationMode(
    feature : NavigationControl,
    val action : UByte,
    val navigationMode: UByte,
    val armed: UByte,
    val res: Long
) : FeatureCommand(feature = feature , commandId = NavigationControl.SET_NAVIGATION_MODE)