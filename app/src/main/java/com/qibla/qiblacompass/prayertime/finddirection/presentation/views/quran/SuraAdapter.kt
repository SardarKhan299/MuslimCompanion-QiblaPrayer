package com.qibla.qiblacompass.prayertime.finddirection.presentation.views.quran

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.qibla.qiblacompass.prayertime.finddirection.R
import com.qibla.qiblacompass.prayertime.finddirection.presentation.views.zakat.ZakatData

class SuraAdapter(private val suraData :List<SuraData>):
RecyclerView.Adapter<SuraAdapter.SuraViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuraViewHolder {
      return SuraViewHolder(
          LayoutInflater.from(parent.context).inflate(R.layout.item_quran_sura_list_layout,parent,false)
      )
    }

    override fun onBindViewHolder(holder: SuraViewHolder, position: Int) {
       val data = suraData[position]
        holder.textSuraNameEnglish.text = data.suraNameEnglish
        holder.textSuraOpen.text = data.suraOpen
        holder.textSuraNumber.text = data.suraNumber.toString()
        holder.textSuraNameUrdu.text = data.suraNameUrdu

    }

    override fun getItemCount(): Int = suraData.size


    inner class SuraViewHolder(itemView :View) :RecyclerView.ViewHolder(itemView) {

        val textSuraNumber: TextView = itemView.findViewById(R.id.tv_sura_number)
        val textSuraNameEnglish: TextView = itemView.findViewById(R.id.tv_quran_sura_name)
        val textSuraOpen: TextView = itemView.findViewById(R.id.tv_quran_sura_open)
        val textSuraNameUrdu: TextView = itemView.findViewById(R.id.tv_quran_sura_name_urdu)

    }
}