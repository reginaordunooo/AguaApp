package orduno.regina.aguaapp

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
    private val viewModel: WaterViewModel by viewModels()
    private lateinit var notifHelper: NotificationHelper
    private val permissionLauncher =
        registerForActivityResult(
            ActivityResultContracts
                .RequestPermission()
        ) { granted ->
            if (granted) {
                sendNotification()
            }
        }
    override fun onCreate(
        savedInstanceState: Bundle?
    ) {
        super.onCreate(savedInstanceState)
        notifHelper = NotificationHelper(this)
        notifHelper.createChannel()
        setContent {
            MaterialTheme {
                Surface {
                    MainScreen(
                        viewModel = viewModel,
                        onSendNotification = {
                            checkPermissionAndSend()
                        }
                    )
                }
            }
        }
    }
    private fun checkPermissionAndSend() {
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
                sendNotification()
            } else {
                permissionLauncher
                    .launch(perm)
            }
        } else {
            sendNotification()
        }
    }
    private fun sendNotification() {
        notifHelper.showNotification()
        viewModel.addRecord()
    }
}