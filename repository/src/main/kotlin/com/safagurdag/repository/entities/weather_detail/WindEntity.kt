package com.safagurdag.repository.entities.weather_detail


import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WindEntity(
    var deg: Int?,
    val speed: Double
)

fun WindEntity.getSpeedWithDenomination(): String = "$speed mph"


/**
 *
 * Finds the direction of the given degree
 * 0 => North
 * 90 => East
 * 180 => South
 * 270 => West
 */
fun WindEntity.getDirection(): String {

    if (deg != null) {

        if (deg!! > 359)
            deg!!.minus(360)

        return when (deg) {

            in 1..89 -> "North East"
            in 91..179 -> "South East"
            in 181..269 -> "North West"
            in 271..359 -> "South West"

            else -> {
                when (deg) {
                    0 -> "North"
                    90 -> "East"
                    180 -> "South"
                    270 -> "West"
                    else -> deg.toString()
                }
            }
        }
    } else
        return "No Data"
}