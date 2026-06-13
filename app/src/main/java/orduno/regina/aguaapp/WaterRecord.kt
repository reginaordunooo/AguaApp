package orduno.regina.aguaapp

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class WaterRecord(
    val timestamp: Date = Date(),
    val drankWater: Boolean? = null
) {
    fun formattedTime(): String {
        return SimpleDateFormat(
            "HH:mm", Locale.getDefault()
        ).format(timestamp)
    }

    fun formattedDate(): String {
        return SimpleDateFormat(
            "dd/MM/yyyy", Locale.getDefault()
        ).format(timestamp)
    }
}