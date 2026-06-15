package orduno.regina.aguaapp

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    viewModel: WaterViewModel,
    onSendNotification: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(stringResource(
                        R.string.main_title
                    ))
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize()
        ) {
// Estado del monitoreo
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    horizontalAlignment =
                        Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(
                            if (viewModel
                                    .isMonitoring.value)
                                R.string.monitoring_active
                            else
                                R.string.monitoring_inactive
                        ),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(
                        modifier = Modifier.height(8.dp)
                    )
                    Button(
                        onClick = onSendNotification
                    ) {
                        Text(stringResource(
                            if (viewModel
                                    .isMonitoring.value)
                                R.string.btn_stop
                            else R.string.btn_start
                        ))
                    }
                }
            }
            Spacer(
                modifier = Modifier.height(16.dp)
            )
            Text(
                text = stringResource(
                    R.string.history_title
                ),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(bottom = 8.dp)
            )
            if (viewModel.records.isEmpty()) {
                Text(
                    text = stringResource(
                        R.string.no_records
                    ),
                    modifier = Modifier
                        .padding(top = 32.dp)
                        .align(
                            Alignment
                                .CenterHorizontally
                        )
                )
            } else {
                LazyColumn {
                    itemsIndexed(
                        viewModel.records
                    ) { index, record ->
                        WaterRecordCard(
                            record = record,
                            index = index,
                            onResponse = {
                                    i, drank ->
                                viewModel
                                    .updateRecord(
                                        i, drank
                                    )
                            }
                        )
                    }
                }
            }
        }
    }
}