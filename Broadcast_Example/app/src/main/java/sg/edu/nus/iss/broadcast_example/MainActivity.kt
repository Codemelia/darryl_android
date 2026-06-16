package sg.edu.nus.iss.broadcast_example

import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import sg.edu.nus.iss.broadcast_example.Receiver.CustomReceiver

class MainActivity : AppCompatActivity() {

    private lateinit var receiver : CustomReceiver

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // initialize the Broadcast Receiver
        receiver = CustomReceiver()

        // setup relevant intent filter for system and custom event actions
        val intentFilter = IntentFilter().apply {
            addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED)
            addAction("sg.edu.nus.iss.broadcast_example.Receiver.MY_ACTION")
        }

        // register the receiver
        registerReceiver(receiver, intentFilter, RECEIVER_EXPORTED)

        // initialise components and event handlers
        val txtDisplay = findViewById<EditText>(R.id.txtDisplayInput);
        findViewById<Button>(R.id.btn_Broadcast_Test).setOnClickListener {
            // 1. take the txtDisplay text
            // 2. put to the custom broadcast intent
            // 3. call sendBroadcast method
            val intent = Intent("sg.edu.nus.iss.broadcast_example.Receiver.MY_ACTION").apply {
                putExtra("DATA", txtDisplay.text.toString())
            }
            sendBroadcast(intent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        unregisterReceiver(receiver)
    }
}