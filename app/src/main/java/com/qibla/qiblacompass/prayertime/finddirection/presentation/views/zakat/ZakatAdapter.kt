package com.qibla.qiblacompass.prayertime.finddirection.presentation.views.zakat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.qibla.qiblacompass.prayertime.finddirection.R

class ZakatAdapter(private val zakatData: List<ZakatData>, private val onItemClick: (ZakatData) -> Unit) :
    RecyclerView.Adapter<ZakatAdapter.ZakatViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ZakatViewHolder {
        return ZakatViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_zakat, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ZakatViewHolder, position: Int) {
        val data = zakatData[position]
        holder.imageZakatType.setImageResource(data.zakatTypeImage)
        holder.textZakatType.text = data.zakatTypeName
        holder.textZakatAmount.text = data.zakatAmount.toString()

    }

    override fun getItemCount(): Int = zakatData.size
    inner class ZakatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageZakatType: ImageView = itemView.findViewById(R.id.img_type_zakat)
        val textZakatType: TextView = itemView.findViewById(R.id.tv_zakat_type_name)
        val textZakatAmount: Button = itemView.findViewById(R.id.btn_zakat)
        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                onItemClick(zakatData[position])
            }
        }
    }


}