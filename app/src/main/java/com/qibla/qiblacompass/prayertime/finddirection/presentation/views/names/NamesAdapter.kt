package com.qibla.qiblacompass.prayertime.finddirection.presentation.views.names

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.qibla.qiblacompass.prayertime.finddirection.R

class NamesAdapter(
    private val namesImages: ArrayList<Int>,
    private val numberImages: ArrayList<Int>,
    private val onItemClick: (position: Int) -> Unit

) :
    RecyclerView.Adapter<NamesAdapter.NamesViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NamesViewHolder {
        return NamesViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.items_names_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: NamesViewHolder, position: Int) {
        val allahNameImage = namesImages[position]
        val numberImage = numberImages[position]
        holder.bind(allahNameImage, numberImage)

    }

    override fun getItemCount(): Int = namesImages.size
    fun setData(newNamesImages: ArrayList<Int>) {
        namesImages.clear()
        namesImages.addAll(newNamesImages)
        notifyDataSetChanged()
    }

    inner class NamesViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val imageViewName: ImageView = itemView.findViewById(R.id.img_names_item)
        val imageViewNameNumber: ImageView = itemView.findViewById(R.id.img_name_number_item)
        val mainbg: View = itemView.findViewById(R.id.view_names)
        init {
            itemView.setOnClickListener {
                onItemClick(adapterPosition)
            }
        }

        fun bind(allahNameImage: Int, numberImage: Int) {
            // Bind data to views
            imageViewName.setImageResource(allahNameImage)
            imageViewNameNumber.setImageResource(numberImage)

        }

    }

}

//class NamesAdapter(
//    private var imageList: ArrayList<NamesData>,
//    private val onItemClick: (NamesData) -> Unit
//
//) :
//    RecyclerView.Adapter<NamesAdapter.NamesViewHolder>() {
//
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NamesViewHolder {
//        return NamesViewHolder(
//            LayoutInflater.from(parent.context)
//                .inflate(R.layout.items_names_layout, parent, false)
//        )
//    }
//
//    override fun onBindViewHolder(holder: NamesViewHolder, position: Int) {
//        val image = imageList[position]
//        holder.imageViewName.setImageResource(image.nameImage)
//        holder.imageViewNameNumber.setImageResource(image.nameNumberImage)
//
//    }
//
//    override fun getItemCount(): Int = imageList.size// Number of items
//
//    fun setData(newImageList: ArrayList<NamesData>) {
//        imageList = newImageList
//        notifyDataSetChanged()
//    }
//
//    inner class NamesViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
//        val imageViewName: ImageView = itemView.findViewById(R.id.img_names_item)
//        val imageViewNameNumber :ImageView = itemView.findViewById(R.id.img_name_number_item)
//        val mainbg :View = itemView.findViewById(R.id.view_names)
//
//        init {
//            mainbg.setOnClickListener {
//                val position = adapterPosition
//                onItemClick(imageList[position])
//            }
//        }
//    }


//}