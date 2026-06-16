package sg.edu.nus.iss.service01_example

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.ResultReceiver
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    private lateinit var etTargetCount: EditText
    private lateinit var btnStart: Button
    private lateinit var tvStatus: TextView
    private lateinit var tvResult: TextView

    private val requestNotificationPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            if (granted) startMyService()
        }

    private val resultReceiver = object : ResultReceiver(Handler(Looper.getMainLooper())) {
        override fun onReceiveResult(resultCode: Int, resultData: Bundle?) {
            if (resultCode == MyService.RESULT_OK) {
                val elapsedMs = resultData?.getLong(MyService.RESULT_KEY_ELAPSED_MS) ?: 0L
                tvStatus.text = "Completed!"
                tvResult.text = "Time taken: ${elapsedMs} ms"
                btnStart.isEnabled = true
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etTargetCount = findViewById(R.id.etTargetCount)
        btnStart = findViewById(R.id.btnStart)
        tvStatus = findViewById(R.id.tvStatus)
        tvResult = findViewById(R.id.tvResult)

        btnStart.setOnClickListener {
            val input = etTargetCount.text.toString()
            val targetCount = input.toLongOrNull()
            if (targetCount == null || targetCount <= 0) {
                Toast.makeText(this, "Please enter a valid positive number", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            tvStatus.text = "Counting to $targetCount…"
            tvResult.text = ""
            btnStart.isEnabled = false

            launchService(targetCount)
        }
    }

    private fun launchService(targetCount: Long) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
            != PackageManager.PERMISSION_GRANTED
        ) {
            requestNotificationPermission.launch(Manifest.permission.POST_NOTIFICATIONS)
            return
        }
        startMyService(targetCount)
    }

    private fun startMyService(targetCount: Long? = etTargetCount.text.toString().toLongOrNull()) {
        val count = targetCount ?: return
        val intent = Intent(this, MyService::class.java).apply {
            putExtra(MyService.EXTRA_TARGET_COUNT, count)
            putExtra(MyService.EXTRA_RESULT_RECEIVER, resultReceiver)
        }
        startForegroundService(intent)
    }
}
