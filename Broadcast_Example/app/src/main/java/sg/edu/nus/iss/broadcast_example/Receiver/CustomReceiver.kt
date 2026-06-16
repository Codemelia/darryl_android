package sg.edu.nus.iss.broadcast_example.Receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class CustomReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        // This method is called when the BroadcastReceiver is receiving an Intent broadcast.
        when (intent.action) {
            Intent.ACTION_AIRPLANE_MODE_CHANGED -> {
                // write your action code here - what you want to perform
                val isEnabled = intent.getBooleanExtra("state", false);
                val message = "My phone airplane mode status: $isEnabled"
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()

            }
            "sg.edu.nus.iss.broadcast_example.Receiver.MY_ACTION" -> {
                val message = intent.getStringExtra("DATA") ?: "No data"
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()

            }
        }
    }
}