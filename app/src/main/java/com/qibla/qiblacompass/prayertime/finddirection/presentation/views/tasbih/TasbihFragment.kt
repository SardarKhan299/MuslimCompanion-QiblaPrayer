package com.qibla.qiblacompass.prayertime.finddirection.presentation.views.tasbih

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.FirebaseApp
import com.google.firebase.appcheck.FirebaseAppCheck
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.qibla.qiblacompass.prayertime.finddirection.R
import com.qibla.qiblacompass.prayertime.finddirection.base.BaseFragment
import com.qibla.qiblacompass.prayertime.finddirection.common.ApplicationConstant.Companion.ALHAMDULILLAH
import com.qibla.qiblacompass.prayertime.finddirection.common.ApplicationConstant.Companion.ALLAHU_AKBAR
import com.qibla.qiblacompass.prayertime.finddirection.common.ApplicationConstant.Companion.LA_ILAHA_ILLA_ALLAH
import com.qibla.qiblacompass.prayertime.finddirection.common.ApplicationConstant.Companion.SUBHAN_ALLAH
import com.qibla.qiblacompass.prayertime.finddirection.common.CommonMethods.Companion.getCurrentDateFormatted
import com.qibla.qiblacompass.prayertime.finddirection.common.SharedPreferences
import com.qibla.qiblacompass.prayertime.finddirection.common.closeCurrentScreen
import com.qibla.qiblacompass.prayertime.finddirection.common.hideActionBar
import com.qibla.qiblacompass.prayertime.finddirection.databinding.FragmentTasbihBinding


class TasbihFragment : BaseFragment<FragmentTasbihBinding>(R.layout.fragment_tasbih) {
    lateinit var recyclerView: RecyclerView
    private lateinit var databaseReference: DatabaseReference
    private lateinit var adapter: TasbihZhikrAdapter
    private lateinit var zhikrTasbihArrayList: ArrayList<ZhikrTasbih>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as AppCompatActivity?)?.hideActionBar()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            tasbihFragment = this@TasbihFragment
        }

        binding.tvTasbihDate.text = getCurrentDateFormatted()

        databaseReference = FirebaseDatabase.getInstance().getReference("ZhikrTasbih")
        zhikrTasbihArrayList = arrayListOf()

        recyclerView = binding.layoutTasbihFragment.findViewById(R.id.recycler_view_zhikr)
        adapter = TasbihZhikrAdapter(requireContext(), zhikrTasbihArrayList, recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())


        fetchDataFromFirebase()

        binding.imgTasbihClose.setOnClickListener {
            findNavController().navigate(R.id.boardFragment)

        }
        binding.imgAddNewTasbih.setOnClickListener {
            findNavController().navigate(R.id.addOwnTasbihFragment)
        }
    }

    private fun fetchDataFromFirebase() {
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                zhikrTasbihArrayList.clear()
                for (childSnapshot in snapshot.children) {
                    val zhikrTasbih = childSnapshot.getValue(ZhikrTasbih::class.java)
                    zhikrTasbih?.let {
                        zhikrTasbihArrayList.add(it)

                    }
                }
                recyclerView.adapter = adapter
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
            }
        })
    }

}


