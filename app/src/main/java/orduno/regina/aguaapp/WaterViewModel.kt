package orduno.regina.aguaapp

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class WaterViewModel : ViewModel() {
    val records = mutableStateListOf<WaterRecord>()
    val isMonitoring = mutableStateOf(false)
    fun addRecord() {
        records.add(0, WaterRecord())
    }
    fun updateRecord(
        index: Int,
        drankWater: Boolean
    ) {
        if (index in records.indices) {
            records[index] = records[index].copy(
                drankWater = drankWater
            )
        }
    }
}