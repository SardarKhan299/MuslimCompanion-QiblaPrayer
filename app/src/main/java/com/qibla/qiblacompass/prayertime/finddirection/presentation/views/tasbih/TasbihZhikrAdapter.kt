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
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.qibla.qiblacompass.prayertime.finddirection.R
import com.qibla.qiblacompass.prayertime.finddirection.common.SharedPreferences
import com.qibla.qiblacompass.prayertime.finddirection.presentation.views.tasbih.TasbihZhikrAdapter.TasbihZhikrViewHolder
import com.bumptech.glide.request.target.Target
import com.google.firebase.database.FirebaseDatabase

class TasbihZhikrAdapter(
    private val context: Context,
    private var data: List<ZhikrTasbih>,
    private val recyclerView: RecyclerView
    // private val onItemClick: (String) -> Unit
) : RecyclerView.Adapter<TasbihZhikrAdapter.TasbihZhikrViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TasbihZhikrViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_tasbih_dhikr, parent, false)
        return TasbihZhikrViewHolder(itemView)
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: TasbihZhikrViewHolder, position: Int) {
        val currentItem = data[position]

//        holder.itemLayout.setOnClickListener {
//            currentItem.zhikrName?.let { it1 -> onItemClick.invoke(it1) }
//        }
//
//        holder.btnStart.setOnClickListener {
//            currentItem.zhikrName?.let { it1 -> onItemClick.invoke(it1) }
//        }

        holder.bind(currentItem)
    }


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

    // Method to delete an item from the adapter and Firebase Realtime Database
//    fun deleteItem(position: Int) {
//        // Check if the item is beyond the first four positions
//        if (position >= 4) {
//            val mutableData = data.toMutableList()
//            val itemToDelete = data[position]
//
//
//            // Show confirmation dialog
//            AlertDialog.Builder(context)
//                .setTitle("Delete Item")
//                .setMessage("Are you sure you want to delete this item?")
//                .setPositiveButton("Yes") { _, _ ->
//                    // Remove item from dataset
//                    // Convert List to MutableList to modify the dataset
//                    mutableData.removeAt(position)
//                    data = mutableData.toList() // Update dataset
//                    notifyItemRemoved(position)
//
//                    // Delete item from Firebase Realtime Database
//                    val databaseReference =
//                        FirebaseDatabase.getInstance().getReference("ZhikrTasbih")
//                    databaseReference.child(itemToDelete.zhikrName.toString())
//                        .child(itemToDelete.zhikrImageUrl.toString())
//                        .removeValue() // Assuming 'key' is the unique identifier of your data item
//                }
//                .setNegativeButton("No", null)
//                .show()
//        } else {
//            // Handle deletion of items from the first four positions
//            // For example, show a toast message indicating that deletion is not allowed
//        }
//    }
//    fun deleteItem(position: Int) {
//        val item = data[position]
//        // Delete from Firebase
//        item.zhikrImageUrl?.let { deleteFromFirebase(it) }
//        // Remove from data list and notify adapter
//        data.toMutableList().removeAt(position)
//        notifyItemRemoved(position)
//    }
//
//    private fun deleteFromFirebase(itemId: String) {
//        // Get Firebase reference for your data
//        val database = FirebaseDatabase.getInstance().reference.child("ZhikrTasbih")
//        database.child(itemId).removeValue()
//    }

//    // Attach swipe-to-delete functionality
//    val itemTouchHelperCallback =
//        object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
//            override fun onMove(
//                recyclerView: RecyclerView,
//                viewHolder: RecyclerView.ViewHolder,
//                target: RecyclerView.ViewHolder
//            ): Boolean {
//                return false
//            }
//
//            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
//                val position = viewHolder.adapterPosition
//                deleteItem(position) // Call deleteItem method when swiped
//            }
//
//            // Override this method to disable swiping for items in the first four positions
//            override fun getSwipeDirs(
//                recyclerView: RecyclerView,
//                viewHolder: RecyclerView.ViewHolder
//            ): Int {
//                val position = viewHolder.adapterPosition
//                return if (position < 4) {
//                    0 // Disable swiping for the first four positions
//                } else {
//                    super.getSwipeDirs(recyclerView, viewHolder)
//                }
//            }
//        }
//
//    val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
}


//class TasbihZhikrAdapter(
//    private val data: List<TasbihZhikrData>,
//    private val onItemClick: (String) -> Unit
//) : RecyclerView.Adapter<TasbihZhikrAdapter.TasbihZhikrViewHolder>() {
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TasbihZhikrViewHolder {
//        val itemView = LayoutInflater.from(parent.context)
//            .inflate(R.layout.item_tasbih_dhikr, parent, false)
//        return TasbihZhikrViewHolder(itemView)
//    }
//
//    override fun onBindViewHolder(holder: TasbihZhikrViewHolder, position: Int) {
//        val currentItem = data[position]
//
//        holder.itemLayout.setOnClickListener {
//            onItemClick.invoke(currentItem.tvZhikr)
//        }
//
//        holder.btnStart.setOnClickListener {
//            onItemClick.invoke(currentItem.tvZhikr)
//        }
//
//        holder.bind(currentItem)
//    }
//
//    override fun getItemCount() = data.size
//
//    inner class TasbihZhikrViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val itemLayout: ConstraintLayout = itemView.findViewById(R.id.item_view_tasbih)
//        val btnStart: Button = itemView.findViewById(R.id.btn_start)
//        fun bind(item: TasbihZhikrData) {
//            if (item.imgZhikrResId != null) {
//                itemView.findViewById<ImageView>(R.id.img_zhikr).setImageResource(item.imgZhikrResId)
//            } else if (item.imgZhikrDrawable != null) {
//                itemView.findViewById<ImageView>(R.id.img_zhikr).setImageDrawable(item.imgZhikrDrawable)
//            }
//            itemView.findViewById<TextView>(R.id.tv_zhikr).text = item.tvZhikr
//        }
//    }
//}
