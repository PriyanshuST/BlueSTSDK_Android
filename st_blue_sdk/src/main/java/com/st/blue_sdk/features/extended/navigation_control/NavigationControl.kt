package com.st.blue_sdk.features.extended.navigation_control

import com.st.blue_sdk.features.Feature
import com.st.blue_sdk.features.FeatureCommand
import com.st.blue_sdk.features.FeatureResponse
import com.st.blue_sdk.features.FeatureUpdate
import com.st.blue_sdk.features.extended.navigation_control.request.CoordinateOrigin
import com.st.blue_sdk.features.extended.navigation_control.request.CurrentCoordinate
import com.st.blue_sdk.features.extended.navigation_control.request.GetRobotTopology
import com.st.blue_sdk.features.extended.navigation_control.request.MoveCommandDifferentialDrivePWMSpeed
import com.st.blue_sdk.features.extended.navigation_control.request.MoveCommandDifferentialDriveSimpleMove
import com.st.blue_sdk.features.extended.navigation_control.request.SetNavigationMode
import com.st.blue_sdk.features.extended.navigation_control.response.NavigationControlResponse
import com.st.blue_sdk.features.extended.navigation_control.response.getNavigationMode
import com.st.blue_sdk.features.extended.navigation_control.response.getTopologyName
import com.st.blue_sdk.utils.NumberConversion

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
        const val MOVE_COMMAND_DIFFERENTIAL_DRIVE_PWM_SPEED : Byte = 0x24
        const val MOVE_COMMAND_DIFFERENTIAL_DRIVE_SIMPLE_MOVE : Byte = 0x25

        fun getCommandType(commandCode: Short) = when (commandCode) {
            0X10.toShort() -> GET_ROBOT_TOPOLOGY
            0X21.toShort() -> SET_NAVIGATION_MODE
            0X22.toShort() -> COORDINATE_ORIGIN
            0X23.toShort() -> CURRENT_COORDINATE
            0X24.toShort() -> MOVE_COMMAND_DIFFERENTIAL_DRIVE_PWM_SPEED
            0x25.toShort() -> MOVE_COMMAND_DIFFERENTIAL_DRIVE_SIMPLE_MOVE

            else -> throw IllegalArgumentException("Unknown command type: $commandCode")
        }

        fun byteArrayToUInt32(data: ByteArray, dataOffset: Int): UInt {
            require(data.size >= dataOffset + 4) { "Not enough bytes to convert to UInt32" }

            return ((data[dataOffset].toUInt() and 0xFFu) shl 24) or
                    ((data[dataOffset + 1].toUInt() and 0xFFu) shl 16) or
                    ((data[dataOffset + 2].toUInt() and 0xFFu) shl 8) or
                    (data[dataOffset + 3].toUInt() and 0xFFu)
        }
    }

    override fun extractData(
        timeStamp: Long,
        data: ByteArray,
        dataOffset: Int
    ): FeatureUpdate<NavigationControlInfo> {
        require(data.size - dataOffset > 0) { "There are no bytes available to read for $name feature" }

        //it contains the first byte after data offset
        val commandId = NumberConversion.byteToUInt8(data,dataOffset)

        val commandType = getCommandType(commandId)

        when (commandType) {
            GET_ROBOT_TOPOLOGY -> {
                val action = NumberConversion.byteToUInt8(data,dataOffset+1).toUByte()
                val topology = byteArrayToUInt32(data,dataOffset + 2)
                val topologyList = getTopologyName(topology)

                return FeatureUpdate(
                    featureName = name,
                    readByte = data.size,
                    timeStamp = timeStamp,
                    rawData = data,
                    data = NavigationControlInfo(
                        commandId = commandId,
                        data = topologyList
                    )
                )
            }

            SET_NAVIGATION_MODE -> {
                val action = NumberConversion.byteToUInt8(data,dataOffset+1).toUByte()
                val navigationModeId = NumberConversion.byteToUInt8(data,dataOffset+2).toUByte()
                val navigationMode = getNavigationMode(navigationModeId)

                return FeatureUpdate(
                    featureName = name,
                    readByte = data.size,
                    timeStamp = timeStamp,
                    rawData = data,
                    data = NavigationControlInfo(
                        commandId = commandId,
                        data = navigationMode
                    )
                )
            }

            else -> return FeatureUpdate(
                featureName = name,
                readByte = data.size,
                timeStamp = timeStamp,
                rawData = data,
                data = NavigationControlInfo(
                    commandId = 0,
                    data = null
                )
            )
        }
    }

    override fun packCommandData(featureBit: Int?, command: FeatureCommand): ByteArray?{
        return when(command){
            is GetRobotTopology -> packCommandRequest(
                featureBit,
                GET_ROBOT_TOPOLOGY,
                byteArrayOf(
                    command.action.toByte(),
                )
            )

            is SetNavigationMode -> packCommandRequest(
                featureBit,
                SET_NAVIGATION_MODE,
                byteArrayOf(
                    command.action.toByte(),
                    command.navigationMode.toByte(),
                    command.armed.toByte(),
                    command.res.toByte()
                )
            )

            is CoordinateOrigin -> packCommandRequest(
                featureBit,
                COORDINATE_ORIGIN,
                byteArrayOf(
                    command.action.toByte(),
                    command.xCoordinate.toByte(),
                    command.yCoordinate.toByte(),
                    command.theta.toByte(),
                    command.res.toByte()
                )
            )

            is CurrentCoordinate -> packCommandRequest(
                featureBit,
                CURRENT_COORDINATE,
                byteArrayOf(
                    command.action.toByte(),
                    command.xCoordinate.toByte(),
                    command.yCoordinate.toByte(),
                    command.theta.toByte(),
                    command.interval.toByte(),
                    command.res.toByte()
                )
            )

            is MoveCommandDifferentialDrivePWMSpeed -> packCommandRequest(
                featureBit,
                MOVE_COMMAND_DIFFERENTIAL_DRIVE_PWM_SPEED,
                byteArrayOf(
                    command.action.toByte() ,
                    command.leftMode.toByte(),
                    command.leftWheel.toByte(),
                    command.rightMode.toByte(),
                    command.rightWheel.toByte(),
                    command.res.toByte()
                )
            )

            is MoveCommandDifferentialDriveSimpleMove -> packCommandRequest(
                featureBit,
                MOVE_COMMAND_DIFFERENTIAL_DRIVE_SIMPLE_MOVE,
                data = byteArrayOf(
                    command.action.toByte() ,
                    command.direction.toByte(),
                    command.speed.toByte(),
                    command.angle.toByte(),
                ) + command.res
            )

            else -> null
        }
    }


    override fun parseCommandResponse(data: ByteArray): FeatureResponse? {
        return unpackCommandResponse(data)?.let {
            if(mask != it.featureMask) return null

            when(it.commandId){
                GET_ROBOT_TOPOLOGY -> {
                    return NavigationControlResponse(
                        feature = this,
                        commandId = GET_ROBOT_TOPOLOGY,
                        payload = it.payload
                    )
                }

                SET_NAVIGATION_MODE -> {
                    return NavigationControlResponse(
                        feature = this,
                        commandId = SET_NAVIGATION_MODE,
                        payload = it.payload
                    )
                }

                else -> null
            }
        }
    }
}