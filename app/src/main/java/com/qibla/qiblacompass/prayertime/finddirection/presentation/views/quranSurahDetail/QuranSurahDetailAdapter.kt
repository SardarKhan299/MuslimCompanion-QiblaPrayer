package com.qibla.qiblacompass.prayertime.finddirection.presentation.views.quranSurahDetail

import android.graphics.Color
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.qibla.qiblacompass.prayertime.finddirection.R

class QuranSurahDetailAdapter(private val surahList: List<QuranSurahDetailData>) : RecyclerView.Adapter<QuranSurahDetailAdapter.QuranSurahDetailViewHolder>() {
    private var clickedPosition = -1
    private var previousClickedPosition = -1
    private var fontSize = 14f // Default font size
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuranSurahDetailViewHolder {
        return QuranSurahDetailViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_quran_surah_detail_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: QuranSurahDetailViewHolder, position: Int) {
        val surah = surahList[position]
        holder.bind(surah)
        // Set background color based on clicked position
        // Set background color based on clicked position
        if (position == clickedPosition) {
            holder.itemView.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.surah_bg_color))
        } else {
            holder.itemView.setBackgroundColor(Color.TRANSPARENT)
        }


        holder.itemView.setOnClickListener {
            // Update clicked position and notify item change
            // Update clicked position and notify item change
            previousClickedPosition = clickedPosition
            clickedPosition = holder.adapterPosition
            notifyItemChanged(previousClickedPosition)
            notifyItemChanged(clickedPosition)
        }

    }
    fun setFontSize(size: Float) {
        fontSize = size
        notifyDataSetChanged() // Notify adapter to update views
    }
    fun increaseFontSize(amount: Float): Float {
        fontSize += amount
        notifyDataSetChanged() // Notify adapter to update views
        return fontSize
    }

    fun decreaseFontSize(amount: Float): Float {
        fontSize -= amount
        notifyDataSetChanged() // Notify adapter to update views
        return fontSize
    }

    override fun getItemCount(): Int = surahList.size


    inner class QuranSurahDetailViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val textArabic: TextView = itemView.findViewById(R.id.tv_arabic_surah)
        private val textEnglish: TextView = itemView.findViewById(R.id.tv_surah_translation_english)
        private val textUrdu: TextView = itemView.findViewById(R.id.tv_surah_translation_urdu)
        fun bind(surah: QuranSurahDetailData) {
            textArabic.text = surah.arabicText
            textEnglish.text = surah.englishTranslation
            textUrdu.text = surah.urduTranslation
            textArabic.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize)
            textEnglish.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize)
            textUrdu.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize)

        }
    }


}