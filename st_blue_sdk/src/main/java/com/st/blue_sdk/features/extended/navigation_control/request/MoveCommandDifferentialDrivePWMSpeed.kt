package com.st.blue_sdk.features.extended.navigation_control.request

import com.st.blue_sdk.features.FeatureCommand
import com.st.blue_sdk.features.extended.navigation_control.NavigationControl

class MoveCommandDifferentialDrivePWMSpeed(
        feature : NavigationControl,
        val action : UByte ,
        val leftMode : UByte = 0x00u,
        val leftWheel : Short,
        val rightMode : UByte = 0x00u,
        val rightWheel : Short,
        val res : Long
    ) : FeatureCommand(feature = feature , commandId = NavigationControl.MOVE_COMMAND_DIFFERENTIAL_DRIVE_PWM_SPEED)