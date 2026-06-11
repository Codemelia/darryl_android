package sg.edu.nus.iss

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import org.json.JSONObject

class ZodiacLinearFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_zodiac_linear, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<ListView>(R.id.listViewZodiac).adapter =
            ZodiacAdapter(requireContext(), loadZodiacData())
    }

    private fun loadZodiacData(): List<ZodiacItem> {
        val json = requireContext().assets.open("sample.json").bufferedReader().use { it.readText() }
        val root = JSONObject(json)
        val array = root.getJSONArray("zodiacs")
        return buildList {
            for (i in 0 until array.length()) {
                val obj = array.getJSONObject(i)
                add(ZodiacItem(
                    name = obj.getString("name"),
                    dateRange = obj.getString("dateRange"),
                    imageUrl = obj.getString("imageUrl"),
                    description = obj.getString("description")
                ))
            }
        }
    }
}
