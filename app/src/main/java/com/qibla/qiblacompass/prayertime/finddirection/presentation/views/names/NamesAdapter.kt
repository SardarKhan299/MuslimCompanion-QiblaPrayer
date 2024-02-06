package com.qibla.qiblacompass.prayertime.finddirection.presentation.views.names

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.qibla.qiblacompass.prayertime.finddirection.R
import com.qibla.qiblacompass.prayertime.finddirection.presentation.views.tasbihcounter.TasbihCounterAdapter

class NamesAdapter(
    private var imageList: ArrayList<NamesData>, private val onItemClick: (NamesData) -> Unit

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
        holder.imageViewName.setImageResource(image.nameImage)
        holder.imageViewNameNumber.setImageResource(image.nameNumberImage)

    }

    override fun getItemCount(): Int = imageList.size// Number of items

    fun setData(newImageList: ArrayList<NamesData>) {
        imageList = newImageList
        notifyDataSetChanged()
    }

    inner class NamesViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val imageViewName: ImageView = itemView.findViewById(R.id.img_names_item)
        val imageViewNameNumber :ImageView = itemView.findViewById(R.id.img_name_number_item)
        val mainbg :View = itemView.findViewById(R.id.view_names)

        init {
            mainbg.setOnClickListener {
                val position = adapterPosition
                onItemClick(imageList[position])
            }
        }
    }


}