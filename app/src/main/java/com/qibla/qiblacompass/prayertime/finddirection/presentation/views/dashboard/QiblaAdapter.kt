package com.qibla.qiblacompass.prayertime.finddirection.presentation.views.dashboard

import android.content.Context
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.qibla.qiblacompass.prayertime.finddirection.R

class QiblaAdapter(
    context: Context, private val qiblaData: List<QiblaData>,
    private val onItemClick: (Int) -> Unit
) : RecyclerView.Adapter<QiblaAdapter.QiblaViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QiblaViewHolder {
        return QiblaViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_qibla_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: QiblaViewHolder, position: Int) {
        val dataQibla = qiblaData[position]
        // Handle item click
        holder.itemView.setOnClickListener {
            onItemClick.invoke(position)
        }
        holder.bgImage.setImageResource(dataQibla.imgBackground)
        holder.titleName.text = dataQibla.nameTitle
    }

    override fun getItemCount() = qiblaData.size

    class QiblaViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val bgImage: ImageView = view.findViewById(R.id.img_item_frame)
        val titleName: TextView = view.findViewById(R.id.tv_title_item)

    }
}

