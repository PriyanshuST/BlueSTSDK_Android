package com.st.blue_sdk.features.extended.navigation_control.request

import com.st.blue_sdk.features.FeatureCommand
import com.st.blue_sdk.features.extended.navigation_control.NavigationControl

class MoveCommandDifferentialDriveSimpleMove(
    feature : NavigationControl,
    val action : UByte,
    val direction : UByte,
    val speed : UByte,
    val angle : Byte,
    val res : ByteArray
) : FeatureCommand(feature = feature , commandId = NavigationControl.MOVE_COMMAND_DIFFERENTIAL_DRIVE_SIMPLE_MOVE)