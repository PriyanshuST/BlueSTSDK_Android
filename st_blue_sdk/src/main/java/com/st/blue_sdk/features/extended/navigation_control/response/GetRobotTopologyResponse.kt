package com.st.blue_sdk.features.extended.navigation_control.response

import com.st.blue_sdk.features.FeatureResponse
import com.st.blue_sdk.features.extended.navigation_control.NavigationControl

class GetRobotTopologyResponse(
    feature : NavigationControl,
    commandId : Byte,
    val payload : ByteArray
) : FeatureResponse(feature = feature, commandId = commandId)

enum class TopologyFeature{
    REMOTE_CONTROL_DIFFERENTIAL,
    REMOTE_CONTROL_STEERING,
    REMOTE_CONTROL_MECHANUM,
    REMOTE_CONTROL_RESERVED,
    ODOMETRY,
    IMU_AVAILABLE,
    ABSOLUTE_SPEED_CONTROL,
    ARM_SUPPORT,
    ATTACHMENT_SUPPORT,
    AUTO_DOCKING ,
    WIRELESS_CHARGING,
    OBSTACLE_DETECTION_FORWARD,
    OBSTACLE_DETECTION_MULTIDIRECTIONAL,
    ALARM,
    HEADLIGHTS,
    WARNING_LIGHTS
}
