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
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
    lateinit var sharedPreferences: SharedPreferences
    lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as AppCompatActivity?)?.hideActionBar()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            tasbihFragment = this@TasbihFragment
        }
        recyclerView = binding.layoutTasbihFragment.findViewById(R.id.recycler_view_zhikr)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.tvTasbihDate.text = getCurrentDateFormatted()
        // Retrieve stored image URI and Tasbih name from SharedPreferences
        val imageUri = SharedPreferences.getImageUri(requireContext())
        val tasbihName = SharedPreferences.getTasbihName(requireContext())
        val data = ArrayList<TasbihZhikrData>()

        // Add the retrieved image URI and Tasbih name to the data list
        data.add(TasbihZhikrData(SUBHAN_ALLAH, R.drawable.ic_subhan_allah))
        data.add(TasbihZhikrData(ALHAMDULILLAH, R.drawable.allhamdulillah))
        data.add(TasbihZhikrData(LA_ILAHA_ILLA_ALLAH, R.drawable.laillaha))
        data.add(TasbihZhikrData(ALLAHU_AKBAR, R.drawable.allahoakbar))
        // Load the image from URI and convert it to a Drawable
        imageUri?.let { uri ->
            val uri1: Uri = Uri.parse(uri)
            val bitmap = MediaStore.Images.Media.getBitmap(requireContext().contentResolver, uri1)
            val drawable = BitmapDrawable(resources, bitmap)

            // Add the retrieved image Drawable and Tasbih name to the data list
            tasbihName?.let { name ->
                data.add(TasbihZhikrData(tvZhikr = name, imgZhikrDrawable = drawable))
            }
        }

        val adapter = TasbihZhikrAdapter(data) { selectedImageName ->
            SharedPreferences.saveImageValue(requireContext(),selectedImageName)
            Log.d("TasbihFragment", "onViewCreated: $selectedImageName")
            Navigation.findNavController(requireView()).navigate(R.id.tasbihCounterFragment)
        }
        recyclerView.adapter = adapter

        binding.imgTasbihClose.setOnClickListener {
            findNavController().closeCurrentScreen()

        }
        binding.imgAddNewTasbih.setOnClickListener {
            findNavController().navigate(R.id.addOwnTasbihFragment)
        }
    }

    // Function to convert Bitmap to Drawable
    fun bitmapToDrawable(bitmap: Bitmap): Drawable {
        return BitmapDrawable(resources, bitmap)
    }
}