package com.st.blue_sdk.features.extended.navigation_control.request

import com.st.blue_sdk.features.FeatureCommand
import com.st.blue_sdk.features.extended.navigation_control.NavigationControl

class GetRobotTopology(
    feature : NavigationControl,
    val action : UByte
) : FeatureCommand(feature = feature, commandId = NavigationControl.GET_ROBOT_TOPOLOGY)