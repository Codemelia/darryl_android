package sg.edu.nus.iss.simpleandroidactivity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.btnOpenViewer).setOnClickListener {
            startActivity(Intent(this, ImageViewerActivity::class.java))
        }
    }
}
