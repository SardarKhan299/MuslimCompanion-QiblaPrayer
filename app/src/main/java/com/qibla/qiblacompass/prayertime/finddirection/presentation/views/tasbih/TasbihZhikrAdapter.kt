package com.qibla.qiblacompass.prayertime.finddirection.presentation.views.tasbih

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.qibla.qiblacompass.prayertime.finddirection.R
import com.qibla.qiblacompass.prayertime.finddirection.common.SharedPreferences
import com.qibla.qiblacompass.prayertime.finddirection.presentation.views.tasbih.TasbihZhikrAdapter.TasbihZhikrViewHolder
class TasbihZhikrAdapter(
    private val data: List<TasbihZhikrData>,
    private val onItemClick: (String) -> Unit
) : RecyclerView.Adapter<TasbihZhikrAdapter.TasbihZhikrViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TasbihZhikrViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_tasbih_dhikr, parent, false)
        return TasbihZhikrViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TasbihZhikrViewHolder, position: Int) {
        val currentItem = data[position]

        holder.itemView.setOnClickListener {
            onItemClick(currentItem.tvZhikr)
        }

        holder.bind(currentItem)
    }

    override fun getItemCount() = data.size

    inner class TasbihZhikrViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: TasbihZhikrData) {
            itemView.findViewById<ImageView>(R.id.img_zhikr).setImageResource(item.imgZhikr)
            itemView.findViewById<TextView>(R.id.tv_zhikr).text = item.tvZhikr
        }
    }
}

//class TasbihZhikrAdapter(
//    private val onItemClick: (TasbihZhikrData) -> Unit
//) :
//    RecyclerView.Adapter<TasbihZhikrViewHolder>() {
//lateinit var sharedPreferences: SharedPreferences
//
//private val data = listOf(
//        TasbihZhikrData("Subhan Allah", R.drawable.ic_subhan_allah),
//        TasbihZhikrData("Alhamdulillah", R.drawable.allhamdulillah),
//        TasbihZhikrData("la ilaha illa Allah", R.drawable.laillaha),
//        TasbihZhikrData("Allahu Akbar", R.drawable.allahoakbar)
//    )
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TasbihZhikrViewHolder {
//        return TasbihZhikrViewHolder(
//            LayoutInflater.from(parent.context)
//                .inflate(R.layout.item_tasbih_dhikr, parent, false)
//        )
//    }
//
//    override fun onBindViewHolder(holder: TasbihZhikrViewHolder, position: Int) {
////        val data = zhikrList[position]
////        holder.zhikrText.text = data.tvZhikr
////        holder.zhikrImage.setImageResource(data.imgZhikr)
////        holder.itemView.setOnClickListener {
////          SharedPreferences.saveSelectedImageKey(holder.zhikrImage.toString())
////        }
//        val currentItem = data[position]
//        holder.bind(currentItem)
//        holder.itemView.setOnClickListener {
//            SharedPreferences.saveImageValue(currentItem.tvZhikr)
//            onItemClick(currentItem)
//            Log.d("TasbihZhikrAdapter", "onBindViewHolder: Item CLicked")
//        }
//    }
//
//    override fun getItemCount() = data.size
//
//
//    inner class TasbihZhikrViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
//        fun bind(item: TasbihZhikrData) {
//            val startBtn: Button = view.findViewById(R.id.btn_start)
//            val zhikrImage: ImageView = view.findViewById(R.id.img_zhikr)
//            val zhikrText: TextView = view.findViewById(R.id.tv_zhikr)
//            zhikrImage.setImageResource(item.imgZhikr)
//            zhikrText.text = item.tvZhikr
//        }
//
//    }
//
//}