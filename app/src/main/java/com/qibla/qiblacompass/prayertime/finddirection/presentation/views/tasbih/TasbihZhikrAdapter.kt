package com.qibla.qiblacompass.prayertime.finddirection.presentation.views.tasbih

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.qibla.qiblacompass.prayertime.finddirection.R
import com.qibla.qiblacompass.prayertime.finddirection.presentation.views.tasbih.TasbihZhikrAdapter.TasbihZhikrViewHolder
import com.bumptech.glide.request.target.Target
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class TasbihZhikrAdapter(
    private val context: Context,
    val data: MutableList<ZhikrTasbih>,
) : RecyclerView.Adapter<TasbihZhikrAdapter.TasbihZhikrViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TasbihZhikrViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_tasbih_dhikr, parent, false)
        return TasbihZhikrViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TasbihZhikrViewHolder, position: Int) {
        val currentItem = data[position]
        holder.bind(currentItem)
    }
    override fun getItemCount() = data.size


    inner class TasbihZhikrViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemLayout: ConstraintLayout = itemView.findViewById(R.id.item_view_tasbih)
        val btnStart: Button = itemView.findViewById(R.id.btn_start)

                fun bind(item: ZhikrTasbih) {
                    //setImageDrawable(item.zhikrImageUrl)
                    itemView.findViewById<TextView>(R.id.tv_zhikr).text = item.zhikrName
                    //  val image = itemView.findViewById<ImageView>(R.id.img_zhikr)
                    Glide.with(context)
                        .load(item.zhikrImageUrl) // Assuming imageUrl is the URL of the image in Firebase Storage
                        .placeholder(R.drawable.ic_tasbih) // Placeholder image while loading
                        .error(R.drawable.ic_new_tasbih) // Image to display if loading fails
                        .listener(object : RequestListener<Drawable> {
                            override fun onLoadFailed(
                                e: GlideException?,
                                model: Any?,
                                target: Target<Drawable>?,
                                isFirstResource: Boolean
                            ): Boolean {
                                // Handle the error here
                                // For example, you can set a placeholder or error image
                                itemView.findViewById<ImageView>(R.id.img_zhikr)
                                    .setImageResource(R.drawable.ic_tasbih)
                                Log.d("TasbihZhikrAdapter", "onLoadFailed: ${e?.message}")
                                return true // Return true to indicate that the error has been handled
                            }

                            override fun onResourceReady(
                                resource: Drawable?,
                                model: Any?,
                                target: Target<Drawable>?,
                                dataSource: DataSource?,
                                isFirstResource: Boolean
                            ): Boolean {
                                // Image loaded successfully
                                return false // Return false to allow Glide to handle the success itself
                            }
                        })
                        .into(itemView.findViewById<ImageView>(R.id.img_zhikr))



                }
            }
}





//        holder.itemLayout.setOnClickListener {
//            currentItem.zhikrName?.let { it1 -> onItemClick.invoke(it1) }
//        }
//
//        holder.btnStart.setOnClickListener {
//            currentItem.zhikrName?.let { it1 -> onItemClick.invoke(it1) }
//        }
