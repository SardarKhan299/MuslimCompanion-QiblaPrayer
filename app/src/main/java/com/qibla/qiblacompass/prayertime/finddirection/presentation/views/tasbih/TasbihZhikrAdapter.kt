package com.qibla.qiblacompass.prayertime.finddirection.presentation.views.tasbih

import android.app.AlertDialog
import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.qibla.qiblacompass.prayertime.finddirection.R
import com.qibla.qiblacompass.prayertime.finddirection.presentation.views.tasbih.TasbihZhikrAdapter.TasbihZhikrViewHolder
import com.bumptech.glide.request.target.Target
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.storage.FirebaseStorage

class TasbihZhikrAdapter(
    private val context: Context,
    val data: MutableList<ZhikrTasbih>,
    private val onItemLongClick: (ZhikrTasbih, Int) -> Unit,
    private val onItemClick: (ZhikrTasbih) -> Unit

) : RecyclerView.Adapter<TasbihZhikrAdapter.TasbihZhikrViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TasbihZhikrViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_tasbih_dhikr, parent, false)
        return TasbihZhikrViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TasbihZhikrViewHolder, position: Int) {
        val currentItem = data[position]
        holder.bind(currentItem)
        // Set long-click listener to delete item
        holder.itemView.setOnLongClickListener {
            onItemLongClick(currentItem, position)
            true
        }
    }

    override fun getItemCount() = data.size

    inner class TasbihZhikrViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemLayout: ConstraintLayout = itemView.findViewById(R.id.item_view_tasbih)
        val btnStart: Button = itemView.findViewById(R.id.btn_start)

        init {

            itemView.setOnClickListener {
                val position = adapterPosition
                onItemClick(data[position])
            }
            btnStart.setOnClickListener {
                val position = adapterPosition
                onItemClick(data[position])
            }
        }


        fun bind(item: ZhikrTasbih) {
            itemView.findViewById<TextView>(R.id.tv_zhikr).text = item.zhikrName
            Glide.with(context)
                .load(item.zhikrImageUrl) // Assuming imageUrl is the URL of the image in Firebase Storage
                .placeholder(R.drawable.ic_tasbih) // Placeholder image while loading
                .error(R.drawable.ic_new_tasbih) // Image to display if loading fails
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
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

