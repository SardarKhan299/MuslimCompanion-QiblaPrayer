package com.qibla.qiblacompass.prayertime.finddirection.presentation.views.quran

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.qibla.qiblacompass.prayertime.finddirection.R

class JuzAdapter(private val juzData: List<JuzData>) :
    RecyclerView.Adapter<JuzAdapter.JuzViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JuzViewHolder {
        return JuzViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_juz_list_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: JuzViewHolder, position: Int) {
        val data = juzData[position]
        holder.textJuzNumber.text = data.juzNumber
        holder.textJuzName.text = data.juzName
        holder.textJuzPage.text = data.juzPage
    }

    override fun getItemCount(): Int = juzData.size


    inner class JuzViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textJuzNumber: TextView = itemView.findViewById(R.id.tv_juz_number)
        val textJuzName: TextView = itemView.findViewById(R.id.tv_juz_name)
        val textJuzPage: TextView = itemView.findViewById(R.id.tv_juz_page_number)
    }

}