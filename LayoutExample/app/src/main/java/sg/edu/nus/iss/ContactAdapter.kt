package sg.edu.nus.iss

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class ContactAdapter(context: Context, private val items: List<ContactItem>) :
    ArrayAdapter<ContactItem>(context, 0, items) {

    private val avatarColors = intArrayOf(
        Color.parseColor("#E03E3E"),
        Color.parseColor("#FF9500"),
        Color.parseColor("#2D7D46"),
        Color.parseColor("#1E6FCC"),
        Color.parseColor("#AF52DE"),
        Color.parseColor("#E85D00"),
        Color.parseColor("#1A7A8C"),
        Color.parseColor("#8B1A4A"),
        Color.parseColor("#5B5EA6"),
        Color.parseColor("#C49000")
    )

    private class ViewHolder(
        val textInitial: TextView,
        val textFullName: TextView,
        val textEmail: TextView,
        val textDob: TextView,
        val textPin: TextView
    )

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View
        val holder: ViewHolder

        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.list_item_contact, parent, false)
            holder = ViewHolder(
                textInitial = view.findViewById(R.id.textInitial),
                textFullName = view.findViewById(R.id.textFullName),
                textEmail = view.findViewById(R.id.textEmail),
                textDob = view.findViewById(R.id.textDob),
                textPin = view.findViewById(R.id.textPin)
            )
            view.tag = holder
        } else {
            view = convertView
            holder = convertView.tag as ViewHolder
        }

        val item = items[position]
        val initial = item.fullName.firstOrNull()?.uppercaseChar() ?: '?'
        val color = avatarColors[initial.lowercaseChar().code % avatarColors.size]

        // Avatar circle with color keyed to first letter
        val avatarDrawable = GradientDrawable().apply {
            shape = GradientDrawable.OVAL
            setColor(color)
        }
        holder.textInitial.background = avatarDrawable
        holder.textInitial.text = initial.toString()

        holder.textFullName.text = item.fullName
        holder.textEmail.text = item.email
        holder.textDob.text = "DOB: ${item.dateOfBirth}"

        // PIN badge uses a smaller tinted circle
        val pinDrawable = GradientDrawable().apply {
            shape = GradientDrawable.RECTANGLE
            cornerRadius = 24f
            setColor(Color.parseColor("#424242"))
        }
        holder.textPin.background = pinDrawable
        holder.textPin.text = "PIN ••••••"

        return view
    }
}
