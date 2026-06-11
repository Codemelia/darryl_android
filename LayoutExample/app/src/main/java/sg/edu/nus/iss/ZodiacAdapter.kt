package sg.edu.nus.iss

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide

class ZodiacAdapter(context: Context, private val items: List<ZodiacItem>) :
    ArrayAdapter<ZodiacItem>(context, 0, items) {

    private class ViewHolder(
        val imageZodiac: ImageView,
        val textName: TextView,
        val textDateRange: TextView,
        val textDescription: TextView
    )

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View
        val holder: ViewHolder

        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.list_item_zodiac, parent, false)
            holder = ViewHolder(
                imageZodiac = view.findViewById(R.id.imageZodiac),
                textName = view.findViewById(R.id.textName),
                textDateRange = view.findViewById(R.id.textDateRange),
                textDescription = view.findViewById(R.id.textDescription)
            )
            view.tag = holder
        } else {
            view = convertView
            holder = convertView.tag as ViewHolder
        }

        val item = items[position]
        holder.textName.text = item.name
        holder.textDateRange.text = item.dateRange
        holder.textDescription.text = item.description

        Glide.with(context)
            .load(item.imageUrl)
            .placeholder(android.R.drawable.ic_menu_gallery)
            .error(android.R.drawable.ic_menu_close_clear_cancel)
            .into(holder.imageZodiac)

        return view
    }
}
