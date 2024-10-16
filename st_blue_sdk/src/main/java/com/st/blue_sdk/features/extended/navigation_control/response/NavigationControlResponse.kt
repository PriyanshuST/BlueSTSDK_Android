package com.st.blue_sdk.features.extended.navigation_control.response

import com.st.blue_sdk.features.FeatureField
import com.st.blue_sdk.features.FeatureResponse
import com.st.blue_sdk.features.extended.navigation_control.NavigationControl

class NavigationControlResponse(
    feature : NavigationControl,
    commandId : Byte,
    val payload : ByteArray
) : FeatureResponse(feature = feature, commandId = commandId)

enum class NavigationResponse {
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
    WARNING_LIGHTS,
    RFU,
    DISARMED,
    ARMED
}

fun isBitSet(value: UInt, bitPosition: Int): Boolean {
    val mask = 1u shl bitPosition
    return (value and mask) != 0u
}

fun getTopologyName(topology: UInt) : List<FeatureField<NavigationResponse>> {

    val functionalities = mutableListOf<FeatureField<NavigationResponse>>()

    for(bitPosition in 0..31){
        if(isBitSet(topology, bitPosition)){
            val functionality = when (bitPosition) {
                0 -> FeatureField(
                    name = "Topology",
                    value = NavigationResponse.REMOTE_CONTROL_DIFFERENTIAL
                )
                1 -> FeatureField(
                    name = "Topology",
                    value = NavigationResponse.REMOTE_CONTROL_STEERING
                )
                2 -> FeatureField(
                    name = "Topology",
                    value = NavigationResponse.REMOTE_CONTROL_MECHANUM
                )
                3 -> FeatureField(
                    name = "Topology",
                    value = NavigationResponse.REMOTE_CONTROL_RESERVED
                )
                4 -> FeatureField(
                    name = "Topology",
                    value = NavigationResponse.ODOMETRY
                )
                5 -> FeatureField(
                    name = "Topology",
                    value = NavigationResponse.IMU_AVAILABLE
                )
                6 -> FeatureField(
                    name = "Topology",
                    value = NavigationResponse.ABSOLUTE_SPEED_CONTROL
                )
                7 -> FeatureField(
                    name = "Topology",
                    value = NavigationResponse.ARM_SUPPORT
                )
                8 -> FeatureField(
                    name = "Topology",
                    value = NavigationResponse.ATTACHMENT_SUPPORT
                )
                9 -> FeatureField(
                    name = "Topology",
                    value = NavigationResponse.AUTO_DOCKING
                )
                10 -> FeatureField(
                    name = "Topology",
                    value = NavigationResponse.WIRELESS_CHARGING
                )
                11 -> FeatureField(
                    name = "Topology",
                    value = NavigationResponse.OBSTACLE_DETECTION_FORWARD
                )
                12 -> FeatureField(
                    name = "Topology",
                    value = NavigationResponse.OBSTACLE_DETECTION_MULTIDIRECTIONAL
                )
                13 -> FeatureField(
                    name = "Topology",
                    value = NavigationResponse.ALARM
                )
                14 -> FeatureField(
                    name = "Topology",
                    value = NavigationResponse.HEADLIGHTS
                )
                15 -> FeatureField(
                    name = "Topology",
                    value = NavigationResponse.WARNING_LIGHTS
                )
                else -> FeatureField(
                    name = "Topology",
                    value = NavigationResponse.RFU // reserved for future use 15 < .. <= 31
                )
            }

            functionality.let { functionalities.add(it) }
        }
    }

    return functionalities
}
