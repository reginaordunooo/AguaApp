package orduno.regina.aguaapp

import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract
.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.core.content.ContextCompat
class MainActivity : ComponentActivity() {
    private val viewModel: WaterViewModel
            by viewModels()
    private lateinit var notifHelper:
            NotificationHelper
    private lateinit var screenReceiver:
            ScreenUnlockReceiver
    private val permissionLauncher =
        registerForActivityResult(
            ActivityResultContracts
                .RequestPermission()
        ) { granted ->
            if (granted) {
                startMonitoring()
            }
        }

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {
        super.onCreate(savedInstanceState)
        notifHelper = NotificationHelper(this)
        notifHelper.createChannel()
// Crear el receiver con el lambda
        screenReceiver =
            ScreenUnlockReceiver {
                notifHelper.showNotification()
                viewModel.addRecord()
            }
        setContent {
            MaterialTheme {
                Surface {
                    MainScreen(
                        viewModel = viewModel,
                        onSendNotification = {
                            toggleMonitoring()
                        }
                    )
                }
            }
        }
    }

    private fun toggleMonitoring() {
        if (viewModel.isMonitoring.value) {
            stopMonitoring()
        } else {
            checkPermissionAndStart()
        }
    }

    private fun checkPermissionAndStart() {
        if (Build.VERSION.SDK_INT >= 33) {
            val perm = android.Manifest
                .permission
                .POST_NOTIFICATIONS
            if (ContextCompat
                    .checkSelfPermission(
                        this, perm
                    )
                == PackageManager
                    .PERMISSION_GRANTED
            ) {
                startMonitoring()
            } else {
                permissionLauncher
                    .launch(perm)
            }
        } else {
            startMonitoring()
        }
    }

    private fun startMonitoring() {
        val filter = IntentFilter(
            Intent.ACTION_USER_PRESENT
        )
        registerReceiver(
            screenReceiver, filter
        )
        viewModel.isMonitoring.value = true
    }

    private fun stopMonitoring() {
        try {
            unregisterReceiver(
                screenReceiver
            )
        } catch (_: Exception) {
        }
        viewModel.isMonitoring.value = false
    }

    override fun onDestroy() {
        super.onDestroy()
        if (viewModel.isMonitoring.value) {
            stopMonitoring()
        }
    }
}