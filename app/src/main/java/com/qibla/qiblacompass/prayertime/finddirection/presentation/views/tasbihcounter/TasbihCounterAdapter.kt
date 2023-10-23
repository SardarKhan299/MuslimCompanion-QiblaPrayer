package com.qibla.qiblacompass.prayertime.finddirection.presentation.views.tasbihcounter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.qibla.qiblacompass.prayertime.finddirection.R
import com.qibla.qiblacompass.prayertime.finddirection.presentation.views.dashboard.QiblaAdapter

class TasbihCounterAdapter(private val tasbihCounterData: List<TasbihCounterData>) :
    RecyclerView.Adapter<TasbihCounterAdapter.TasbihCounterViewModel>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TasbihCounterViewModel {
        return TasbihCounterViewModel(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_tasbih_counter_backround_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: TasbihCounterViewModel, position: Int) {
        val counter = tasbihCounterData[position]
        holder.counterImage.setImageResource(counter.counterImageBg)
    }

    override fun getItemCount() = tasbihCounterData.size


    class TasbihCounterViewModel(val view: View) : RecyclerView.ViewHolder(view) {
        val counterImage: ImageView = view.findViewById(R.id.img_tasbih_counter)
    }


}