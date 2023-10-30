package com.qibla.qiblacompass.prayertime.finddirection.presentation.views.tasbihcounter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.qibla.qiblacompass.prayertime.finddirection.R

class TasbihCounterAdapter(
    private val imageList: List<Int>, private val onItemClick: (Int) -> Unit

) :
    RecyclerView.Adapter<TasbihCounterAdapter.TasbihCounterViewModel>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TasbihCounterViewModel {
        return TasbihCounterViewModel(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_tasbih_counter_backround_layout, parent, false)
        )
    }

    override fun getItemCount(): Int = imageList.size// Number of items

    override fun onBindViewHolder(holder: TasbihCounterViewModel, position: Int) {
        val image = imageList[position]
        holder.imageView.setImageResource(image)

    }

    inner class TasbihCounterViewModel(val view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = itemView.findViewById(R.id.img_tasbih_counter)

        init {
            imageView.setOnClickListener {
                val position = adapterPosition
                onItemClick(imageList[position])
            }
        }

    }
}
