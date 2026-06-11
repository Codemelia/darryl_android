package sg.edu.nus.iss

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.Fragment
import org.json.JSONArray
import java.io.File

class ContactListFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_contact_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val listView = view.findViewById<ListView>(R.id.listViewContacts)
        val textEmpty = view.findViewById<TextView>(R.id.textEmpty)

        // Wire the ListView empty-state view before setting the adapter
        listView.emptyView = textEmpty

        listView.adapter = ContactAdapter(requireContext(), loadContacts())
    }

    private fun loadContacts(): List<ContactItem> {
        val file = File(requireContext().filesDir, "contact.json")
        if (!file.exists()) return emptyList()

        val array = JSONArray(file.readText())
        return buildList {
            for (i in 0 until array.length()) {
                val obj = array.getJSONObject(i)
                add(ContactItem(
                    fullName = obj.getString("fullName"),
                    email = obj.getString("email"),
                    dateOfBirth = obj.getString("dateOfBirth"),
                    pinCode = obj.getString("pinCode")
                ))
            }
        }
    }
}
