package com.st.blue_sdk.features.extended.navigation_control

import com.st.blue_sdk.features.FeatureField
import com.st.blue_sdk.features.extended.navigation_control.response.NavigationResponse
import com.st.blue_sdk.logger.Loggable

data class NavigationControlInfo(
    val commandId : Short,
    val data : List<FeatureField<NavigationResponse>>?
): Loggable {
    override val logHeader: String = ""

    override val logValue: String = ""

    override fun toString(): String = "To Be Implemented"

    override val logDoubleValues: List<Double> = listOf()

}