package sg.edu.nus.iss.simpleandroidactivity

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ImageAdapter : RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

    private val colors = listOf(
        Color.parseColor("#FFEF9A9A"), Color.parseColor("#FFF48FB1"), Color.parseColor("#FFCE93D8"),
        Color.parseColor("#FF9FA8DA"), Color.parseColor("#FF90CAF9"), Color.parseColor("#FF80DEEA"),
        Color.parseColor("#FFA5D6A7"), Color.parseColor("#FFE6EE9C"), Color.parseColor("#FFFFE082"),
        Color.parseColor("#FFFFCC80"), Color.parseColor("#FFBCAAA4"), Color.parseColor("#FFB0BEC5"),
        Color.parseColor("#FF80CBC4"), Color.parseColor("#FF80DEEA"), Color.parseColor("#FFC5E1A5")
    )

    class ImageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: SquareImageView = view.findViewById(R.id.imageView)
        val textLabel: TextView = view.findViewById(R.id.textLabel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_image, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.imageView.setBackgroundColor(colors[position])
        holder.textLabel.text = "Image ${position + 1}"
    }

    override fun getItemCount() = 15
}
