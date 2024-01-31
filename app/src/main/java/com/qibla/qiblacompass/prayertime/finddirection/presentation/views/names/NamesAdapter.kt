package com.qibla.qiblacompass.prayertime.finddirection.presentation.views.names

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.qibla.qiblacompass.prayertime.finddirection.R
import com.qibla.qiblacompass.prayertime.finddirection.presentation.views.tasbihcounter.TasbihCounterAdapter

class NamesAdapter(
    private val imageList: List<Int>, private val onItemClick: (Int) -> Unit

) :
    RecyclerView.Adapter<NamesAdapter.NamesViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NamesViewHolder {
        return NamesViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.items_names_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: NamesViewHolder, position: Int) {
        val image = imageList[position]
        holder.imageView.setImageResource(image)
    }

    override fun getItemCount(): Int = imageList.size// Number of items


    inner class NamesViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = itemView.findViewById(R.id.img_names)

        init {
            imageView.setOnClickListener {
                val position = adapterPosition
                onItemClick(imageList[position])
            }
        }
    }


}