package sg.edu.nus.iss.service01_example

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.os.ResultReceiver
import androidx.core.app.NotificationCompat

class MyService : Service() {

    companion object {
        const val EXTRA_TARGET_COUNT = "TARGET_COUNT"
        const val EXTRA_RESULT_RECEIVER = "RESULT_RECEIVER"
        const val RESULT_KEY_ELAPSED_MS = "ELAPSED_MS"
        const val RESULT_OK = 0

        private const val CHANNEL_ID = "my_service_channel"
        private const val NOTIFICATION_ID = 1
    }

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        startForeground(NOTIFICATION_ID, buildNotification())
    }

    override fun onBind(intent: Intent): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val targetCount = intent?.getLongExtra(EXTRA_TARGET_COUNT, 0L) ?: 0L
        val resultReceiver = intent?.getResultReceiver()

        if (targetCount > 0L && resultReceiver != null) {
            Thread {
                val startTime = System.currentTimeMillis()

                for (i in 0L..targetCount) {
                    // counting
                }

                val elapsedMs = System.currentTimeMillis() - startTime
                val bundle = Bundle().apply { putLong(RESULT_KEY_ELAPSED_MS, elapsedMs) }
                resultReceiver.send(RESULT_OK, bundle)
            }.start()
        }

        return START_STICKY
    }

    private fun buildNotification(): Notification =
        NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("MyService")
            .setContentText("Running in background, waiting for requests…")
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setOngoing(true)
            .build()

    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            CHANNEL_ID,
            "MyService Background",
            NotificationManager.IMPORTANCE_LOW
        )
        getSystemService(NotificationManager::class.java).createNotificationChannel(channel)
    }

    private fun Intent.getResultReceiver(): ResultReceiver? =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            getParcelableExtra(EXTRA_RESULT_RECEIVER, ResultReceiver::class.java)
        } else {
            @Suppress("DEPRECATION")
            getParcelableExtra(EXTRA_RESULT_RECEIVER)
        }
}
