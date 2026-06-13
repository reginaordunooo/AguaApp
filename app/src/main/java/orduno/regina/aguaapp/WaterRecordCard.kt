package orduno.regina.aguaapp

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun WaterRecordCard(
    record: WaterRecord,
    index: Int,
    onResponse: (Int, Boolean) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = when
                                     (record.drankWater) {
                true ->
                    MaterialTheme.colorScheme
                        .primaryContainer
                false ->
                    MaterialTheme.colorScheme
                        .errorContainer
                null ->
                    MaterialTheme.colorScheme
                        .surfaceVariant
            }
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = stringResource(
                    R.string.reminder_received
                ),
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "${record.formattedDate()}"
                        + " ${record.formattedTime()}"
            )
            if (record.drankWater == null) {
                Row(
                    modifier = Modifier
                        .padding(top = 8.dp),
                    horizontalArrangement =
                        Arrangement
                            .spacedBy(8.dp)
                ) {
                    Button(
                        onClick = {
                            onResponse(
                                index, true
                            )
                        }
                    ) {
                        Text(stringResource(
                            R.string.drank_water
                        ))
                    }
                    OutlinedButton(
                        onClick = {
                            onResponse(
                                index, false
                            )
                        }
                    ) {
                        Text(stringResource(
                            R.string.skipped_water
                        ))
                    }
                }
            }
        }
    }
}