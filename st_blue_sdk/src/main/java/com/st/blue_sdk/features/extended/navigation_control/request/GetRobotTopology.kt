package com.st.blue_sdk.features.extended.navigation_control.request

import com.st.blue_sdk.features.FeatureCommand
import com.st.blue_sdk.features.extended.navigation_control.NavigationControl
import com.st.blue_sdk.features.extended.navigation_control.NavigationControl.Companion.COORDINATE_ORIGIN
import com.st.blue_sdk.features.extended.navigation_control.NavigationControl.Companion.CURRENT_COORDINATE
import com.st.blue_sdk.features.extended.navigation_control.NavigationControl.Companion.GET_ROBOT_TOPOLOGY
import com.st.blue_sdk.features.extended.navigation_control.NavigationControl.Companion.MOVE_COMMAND_DIFFERENTIAL_DRIVE
import com.st.blue_sdk.features.extended.navigation_control.NavigationControl.Companion.SET_NAVIGATION_MODE

class GetRobotTopology(
    feature : NavigationControl,
    val action : UByte
) : FeatureCommand(feature = feature, commandId = NavigationControl.GET_ROBOT_TOPOLOGY)

