package orduno.regina.aguaapp

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat

class NotificationHelper(
    private val context: Context
) {
    companion object {
        const val CHANNEL_ID = "water_reminder"
        const val NOTIFICATION_ID = 1
    }
    fun createChannel() {
        val name = context.getString(
            R.string.notif_channel_name
        )
        val desc = context.getString(
            R.string.notif_channel_desc
        )
        val channel = NotificationChannel(
            CHANNEL_ID,
            name,
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            description = desc
        }
        val manager = context.getSystemService(
            NotificationManager::class.java
        )
        manager.createNotificationChannel(channel)
    }
    fun showNotification() {
// Intent para abrir la app al tocar
        val intent = Intent(
            context, MainActivity::class.java
        ).apply {
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
        }
        val pendingIntent = PendingIntent
            .getActivity(
                context, 0, intent,
                PendingIntent.FLAG_IMMUTABLE
            )
        val builder = NotificationCompat.Builder(
            context, CHANNEL_ID
        )
            .setSmallIcon(
                android.R.drawable.ic_dialog_info
            )
            .setContentTitle(
                context.getString(
                    R.string.notif_title
                )
            )
            .setContentText(
                context.getString(
                    R.string.notif_text
                )
            )
            .setPriority(
                NotificationCompat.PRIORITY_DEFAULT
            )
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
        val manager = context.getSystemService(
            NotificationManager::class.java
        )
        manager.notify(
            NOTIFICATION_ID, builder.build()
        )
    }
}