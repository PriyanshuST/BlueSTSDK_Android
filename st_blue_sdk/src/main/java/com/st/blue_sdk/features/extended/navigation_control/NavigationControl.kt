package com.st.blue_sdk.features.extended.navigation_control

import com.st.blue_sdk.features.Feature
import com.st.blue_sdk.features.FeatureCommand
import com.st.blue_sdk.features.FeatureResponse
import com.st.blue_sdk.features.FeatureUpdate
import com.st.blue_sdk.features.extended.navigation_control.request.GetRobotTopology
import com.st.blue_sdk.features.extended.navigation_control.request.MoveCommandDifferentialDrive
import com.st.blue_sdk.features.extended.navigation_control.response.GetRobotTopologyResponse
import java.nio.ByteBuffer
import java.nio.ByteOrder

class NavigationControl(
    name: String = NAME,
    type: Type = Type.EXTENDED,
    isEnabled: Boolean,
    identifier: Int
) : Feature<NavigationControlInfo>(
    name = name,
    type = type,
    isEnabled = isEnabled,
    identifier = identifier
) {
    companion object {
        const val NAME = "Navigation Control"
        const val NUMBER_BYTES = 2
        const val GET_ROBOT_TOPOLOGY : Byte = 0x10
        const val SET_NAVIGATION_MODE : Byte = 0x21
        const val COORDINATE_ORIGIN : Byte = 0x22
        const val CURRENT_COORDINATE : Byte = 0x23
        const val MOVE_COMMAND_DIFFERENTIAL_DRIVE : Byte = 0x24
    }

    override fun extractData(
        timeStamp: Long,
        data: ByteArray,
        dataOffset: Int
    ): FeatureUpdate<NavigationControlInfo> {
        require(data.size - dataOffset > 0) { "There are no bytes available to read for $name feature" }

        return FeatureUpdate(
            featureName = name,
            readByte = data.size,
            timeStamp = timeStamp,
            rawData = data,
            data = NavigationControlInfo(name = "Place holder")
        )
    }

    override fun packCommandData(featureBit: Int?, command: FeatureCommand): ByteArray?{
        return when(command){
            is MoveCommandDifferentialDrive -> packCommandRequest(
                featureBit,
                MOVE_COMMAND_DIFFERENTIAL_DRIVE,
                byteArrayOf(
                    command.action.toByte() ,
                    command.leftMode.toByte(),
                    command.leftWheel.toByte(),
                    command.rightMode.toByte(),
                    command.rightWheel.toByte(),
                    command.res.toByte()
                )
            )

            is GetRobotTopology -> packCommandRequest(
                featureBit,
                GET_ROBOT_TOPOLOGY,
                byteArrayOf(
                    command.action.toByte(),
                )
            )

            else -> null
        }
    }


    override fun parseCommandResponse(data: ByteArray): FeatureResponse? {
        return unpackCommandResponse(data)?.let {
            if(mask != it.featureMask) return null

            when(it.commandId){
                GET_ROBOT_TOPOLOGY -> {
                    val topology: Long = ByteBuffer.wrap(it.payload,2,4)
                        .order(ByteOrder.BIG_ENDIAN)
                        .int.toLong()

                    return GetRobotTopologyResponse(
                        feature = this,
                        commandId = GET_ROBOT_TOPOLOGY,
                        payload = it.payload
                    )
                }

                else -> null
            }
        }
    }
}