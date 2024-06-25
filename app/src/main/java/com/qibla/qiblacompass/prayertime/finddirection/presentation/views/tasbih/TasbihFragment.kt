package com.qibla.qiblacompass.prayertime.finddirection.presentation.views.tasbih

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.qibla.qiblacompass.prayertime.finddirection.R
import com.qibla.qiblacompass.prayertime.finddirection.base.BaseFragment
import com.qibla.qiblacompass.prayertime.finddirection.common.AdUtil
import com.qibla.qiblacompass.prayertime.finddirection.common.CommonMethods.Companion.getCurrentDateFormatted
import com.qibla.qiblacompass.prayertime.finddirection.common.ProgressBar
import com.qibla.qiblacompass.prayertime.finddirection.common.hideActionBar
import com.qibla.qiblacompass.prayertime.finddirection.databinding.FragmentTasbihBinding
import com.qibla.qiblacompass.prayertime.finddirection.presentation.views.dashboard.DashBoardFragment


class TasbihFragment : BaseFragment<FragmentTasbihBinding>(R.layout.fragment_tasbih) {
    lateinit var recyclerView: RecyclerView
    private lateinit var databaseReference: DatabaseReference
    private lateinit var adapter: TasbihZhikrAdapter
    private lateinit var zhikrTasbihArrayList: MutableList<ZhikrTasbih>
    private lateinit var swipeHelper: ItemTouchHelper
    private lateinit var adView: AdView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as AppCompatActivity?)?.hideActionBar()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            tasbihFragment = this@TasbihFragment
        }
        databaseReference = FirebaseDatabase.getInstance().getReference("ZhikrTasbih")
        binding.tvTasbihDate.text = getCurrentDateFormatted()
        zhikrTasbihArrayList = mutableListOf()

        recyclerView = binding.layoutTasbihFragment.findViewById(R.id.recycler_view_zhikr)
        adapter = TasbihZhikrAdapter(mContext, zhikrTasbihArrayList, { item, position ->
            showDeleteConfirmationDialog(item, position)
        },{findNavController().navigate(R.id.tasbihCounterFragment)})


        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.imgTasbihClose.setOnClickListener {
            findNavController().navigate(R.id.boardFragment)
        }

        binding.imgAddNewTasbih.setOnClickListener {
            findNavController().navigate(R.id.addOwnTasbihFragment)
        }

        ProgressBar.hideProgressBar()
        fetchDataFromFirebase()
        setupSwipeToShowButtons()

        MobileAds.initialize(mContext) {
            Log.d(
                DashBoardFragment::class.java.simpleName,
                "onViewCreated: onInitializationCompleted"
            )
        }
        adView = binding.adsBannerTasbih
        // Initialize AdUtil and load the banner ad
        AdUtil.initialize(requireContext(), adView)
    }

    private fun showDeleteConfirmationDialog(item: ZhikrTasbih, position: Int) {
        AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.delete_text_title))
            .setMessage(getString(R.string.delete_message))
            .setPositiveButton(getString(R.string.yes)) { dialog, which ->
                deleteItem(item, position)
            }
            .setNegativeButton(getString(R.string.no), null)
            .show()
    }

    private fun deleteItem(item: ZhikrTasbih, position: Int) {
//        if (item.zhikrName == "t1" || item.zhikrName == "t2") {
//            Toast.makeText(requireContext(), "This item cannot be deleted", Toast.LENGTH_SHORT)
//                .show()
//            return
//        }

        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        val itemRef = databaseReference.child(userId).child(item.key.toString())

        // Remove value from Firebase Realtime Database
        itemRef.removeValue().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d(
                    TasbihFragment::class.java.simpleName,
                    "Data removed from Realtime Database successfully"
                )
                // Remove associated image from Firebase Storage
                val storageRef =
                    FirebaseStorage.getInstance()
                        .getReferenceFromUrl(item.zhikrImageUrl.toString())
                storageRef.delete().addOnCompleteListener { deleteTask ->
                    if (deleteTask.isSuccessful) {
                        Toast.makeText(
                            mContext,
                            "Item deleted successfully",
                            Toast.LENGTH_SHORT
                        ).show()
                        // Remove the item from the adapter and notify the change
                        zhikrTasbihArrayList.removeAt(position)
                        adapter.notifyItemRemoved(position)
                    } else {
                        Toast.makeText(mContext, "Failed to delete image", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            } else {
                Log.e(
                    TasbihFragment::class.java.simpleName,
                    "Failed to remove data from Realtime Database: ${task.exception?.message}"
                )
                Toast.makeText(mContext, "Failed to delete item", Toast.LENGTH_SHORT).show()
            }
        }

    }

//    private fun setupSwipeToShowButtons() {
//        val swipeCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
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
//                val item = zhikrTasbihArrayList[position]
//                showDeleteConfirmationDialog(item, position)
//                adapter.notifyItemChanged(position)
//            }
//
//            override fun onChildDraw(
//                c: Canvas,
//                recyclerView: RecyclerView,
//                viewHolder: RecyclerView.ViewHolder,
//                dX: Float,
//                dY: Float,
//                actionState: Int,
//                isCurrentlyActive: Boolean
//            ) {
//                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
//                    val itemView = viewHolder.itemView
//                    val paint = Paint().apply {
//                        color = Color.RED
//                    }
//                    c.drawRect(
//                        itemView.left.toFloat(),
//                        itemView.top.toFloat(),
//                        itemView.left.toFloat() + dX,
//                        itemView.bottom.toFloat(),
//                        paint
//                    )
//
////                    val textPaint = Paint().apply {
////                        color = Color.WHITE
////                        textSize = 40f
////                    }
//                    // Set up the text paint with desired styles
//                    val textPaint = Paint().apply {
//                        color = Color.WHITE
//                        textSize = 30f
//                        isAntiAlias = true
//                      //  typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
//                        typeface = ResourcesCompat.getFont(mContext, R.font.poppins_italic)
//
//                    }
//                    val text = "Delete"
//                    val textWidth = textPaint.measureText(text)
//                    val textMargin = (itemView.height - textPaint.textSize) / 2
//                    c.drawText(
//                        text,
//                        itemView.left + textMargin,
//                        itemView.top + itemView.height / 2 + textPaint.textSize / 2,
//                        textPaint
//                    )
//
//                    val alpha = 1.0f - Math.abs(dX) / recyclerView.width.toFloat()
//                    viewHolder.itemView.alpha = alpha
//                    viewHolder.itemView.translationX = dX
//                } else {
//                    super.onChildDraw(
//                        c,
//                        recyclerView,
//                        viewHolder,
//                        dX,
//                        dY,
//                        actionState,
//                        isCurrentlyActive
//                    )
//                }
//            }
//        }
//
//        val itemTouchHelper = ItemTouchHelper(swipeCallback)
//        itemTouchHelper.attachToRecyclerView(binding.recyclerViewZhikr)
//    }

    private fun setupSwipeToShowButtons() {
        val swipeCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val item = zhikrTasbihArrayList[position]
                showDeleteConfirmationDialog(item, position)
                adapter.notifyItemChanged(position)
            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                    val itemView = viewHolder.itemView
                    val paint = Paint().apply {
                        color = Color.RED
                    }

                    // Draw the red delete background on the left side
                    c.drawRect(
                        itemView.right.toFloat() + dX,
                        itemView.top.toFloat(),
                        itemView.right.toFloat(),
                        itemView.bottom.toFloat(),
                        paint
                    )

                    // Set up the text paint with desired styles
                    val textPaint = Paint().apply {
                        color = Color.WHITE
                        textSize = 30f
                        isAntiAlias = true
                        typeface = ResourcesCompat.getFont(mContext, R.font.poppins_italic)
                    }

                    val text = "Delete"
                    val textWidth = textPaint.measureText(text)
                    val textMargin = (itemView.height - textPaint.textSize) / 2

                    // Draw the text on the left side
                    c.drawText(
                        text,
                        itemView.right - textWidth - textMargin,
                        itemView.top + itemView.height / 2 + textPaint.textSize / 2,
                        textPaint
                    )

                    val alpha = 1.0f - Math.abs(dX) / recyclerView.width.toFloat()
                    viewHolder.itemView.alpha = alpha
                    viewHolder.itemView.translationX = dX
                } else {
                    super.onChildDraw(
                        c,
                        recyclerView,
                        viewHolder,
                        dX,
                        dY,
                        actionState,
                        isCurrentlyActive
                    )
                }
            }
        }

        val itemTouchHelper = ItemTouchHelper(swipeCallback)
        itemTouchHelper.attachToRecyclerView(binding.recyclerViewZhikr)
    }


    private fun fetchDataFromFirebase() {
        ProgressBar.showProgressBar(mContext,"Please wait...")
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        val guestRef = databaseReference
        if (userId != null) {
            userId.let { databaseReference.child(it) }
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        zhikrTasbihArrayList.clear()
                        for (zhikrSnapshot in snapshot.children) {
                            val key = zhikrSnapshot.key ?: continue
                            val zhikrName =
                                zhikrSnapshot.child("zhikrName").getValue(String::class.java)
                                    ?: continue
                            val zhikrImageUrl =
                                zhikrSnapshot.child("zhikrImageUrl").getValue(String::class.java)
                                    ?: continue

                            val zhikrItem = ZhikrTasbih(zhikrName, zhikrImageUrl, key)
                            zhikrTasbihArrayList.add(zhikrItem)
                        }
                        recyclerView.adapter = adapter
                        ProgressBar.hideProgressBar()  // Hide the progress bar after data is fetched
                    }
                    override fun onCancelled(error: DatabaseError) {
                        ProgressBar.hideProgressBar()
                        Log.e(
                            TasbihFragment::class.java.simpleName,
                            "Database error: ${error.message}"
                        )
                    }
                })
        } else {
            guestRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (zhikrSnapshot in snapshot.children) {
                        val key = zhikrSnapshot.key ?: continue
                        val zhikrName =
                            zhikrSnapshot.child("zhikrName").getValue(String::class.java)
                                ?: continue
                        val zhikrImageUrl =
                            zhikrSnapshot.child("zhikrImageUrl").getValue(String::class.java)
                                ?: continue

                        val zhikrItem = ZhikrTasbih(zhikrName, zhikrImageUrl)
                        zhikrTasbihArrayList.add(zhikrItem)
                    }
                    recyclerView.adapter = adapter
                    ProgressBar.hideProgressBar()  // Hide the progress bar after data is fetched
                }

                override fun onCancelled(error: DatabaseError) {
                    ProgressBar.hideProgressBar()  // Hide the progress bar after data is fetched
                    // Handle error
                    Log.e(
                        TasbihFragment::class.java.simpleName,
                        "Guest Database error: ${error.message}"
                    )
                }
            })
        }
    }
    override fun onResume() {
        adView.resume()
        super.onResume()
        Log.d(TAG, "onResume: ")
    }

    override fun onPause() {
        adView.pause()
        super.onPause()
        Log.d(TAG, "onPause: ")
    }

    override fun onDestroy() {
        adView.destroy()
        super.onDestroy()
        Log.d(TAG, "onDestroy: ")
    }
    companion object{
        private val TAG = "TasbihFragment"
    }
}

