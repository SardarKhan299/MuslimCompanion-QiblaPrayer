package com.qibla.qiblacompass.prayertime.finddirection.presentation.views.qibaldirection

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.qibla.qiblacompass.prayertime.finddirection.R

class CompassAdapter(
    private val compassImageList: List<Int>,
    private val onItemClick: (Int) -> Unit
) : RecyclerView.Adapter<CompassAdapter.CompassViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompassViewHolder {
        return CompassViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_compass_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: CompassViewHolder, position: Int) {
        val imageCompass = compassImageList[position]
        holder.imageView.setImageResource(imageCompass)
    }

    override fun getItemCount(): Int = compassImageList.size

    inner class CompassViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = itemView.findViewById(R.id.img_compass_direction)

        init {
            imageView.setOnClickListener {
                val position = adapterPosition
                onItemClick(compassImageList[position])
            }
        }
    }


}