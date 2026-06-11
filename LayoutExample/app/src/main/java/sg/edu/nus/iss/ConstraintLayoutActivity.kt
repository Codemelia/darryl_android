package sg.edu.nus.iss

import android.os.Bundle
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONObject

class ConstraintLayoutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_zodiac_constraint)

        val listView = findViewById<ListView>(R.id.listViewZodiac)
        listView.adapter = ZodiacAdapter(this, loadZodiacData())
    }

    private fun loadZodiacData(): List<ZodiacItem> {
        val json = assets.open("sample.json").bufferedReader().use { it.readText() }
        val root = JSONObject(json)
        val array = root.getJSONArray("zodiacs")

        return buildList {
            for (i in 0 until array.length()) {
                val obj = array.getJSONObject(i)
                add(
                    ZodiacItem(
                        name = obj.getString("name"),
                        dateRange = obj.getString("dateRange"),
                        imageUrl = obj.getString("imageUrl"),
                        description = obj.getString("description")
                    )
                )
            }
        }
    }
}
