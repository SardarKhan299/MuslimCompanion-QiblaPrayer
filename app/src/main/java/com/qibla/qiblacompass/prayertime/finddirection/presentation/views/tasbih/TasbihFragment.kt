package com.qibla.qiblacompass.prayertime.finddirection.presentation.views.tasbih

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.qibla.qiblacompass.prayertime.finddirection.R
import com.qibla.qiblacompass.prayertime.finddirection.base.BaseFragment
import com.qibla.qiblacompass.prayertime.finddirection.common.CommonMethods.Companion.getCurrentDateFormatted
import com.qibla.qiblacompass.prayertime.finddirection.common.hideActionBar
import com.qibla.qiblacompass.prayertime.finddirection.databinding.FragmentTasbihBinding


class TasbihFragment : BaseFragment<FragmentTasbihBinding>(R.layout.fragment_tasbih) {
    lateinit var recyclerView: RecyclerView
    private lateinit var databaseReference: DatabaseReference
    private lateinit var adapter: TasbihZhikrAdapter
    private lateinit var zhikrTasbihArrayList: MutableList<ZhikrTasbih>
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

// Write a message to the database
//        val database = Firebase.database
//        val myRef = database.getReference("message")
//
//        myRef.setValue("Hello, World!")
        // Read from the database
//        myRef.addValueEventListener(object: ValueEventListener {
//
//            override fun onDataChange(snapshot: DataSnapshot) {
//                // This method is called once with the initial value and again
//                // whenever data at this location is updated.
//                val value = snapshot.getValue<String>()
//                Log.d(TasbihFragment::class.simpleName, "Value is: " + value)
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                Log.w(TasbihFragment::class.simpleName, "Failed to read value.", error.toException())
//            }
//
//        })

// Remove the data under the "message" node
//        myRef.removeValue()
//            .addOnSuccessListener {
//                Log.d(TasbihFragment::class.simpleName, "Data deleted successfully")
//            }
//            .addOnFailureListener { error ->
//                Log.w(TasbihFragment::class.simpleName, "Failed to delete data", error)
//            }
        binding.tvTasbihDate.text = getCurrentDateFormatted()


        zhikrTasbihArrayList = mutableListOf()

        recyclerView = binding.layoutTasbihFragment.findViewById(R.id.recycler_view_zhikr)
        adapter = TasbihZhikrAdapter(requireContext(), zhikrTasbihArrayList) { item, position ->
            showDeleteConfirmationDialog(item, position)
        }
        recyclerView.layoutManager = LinearLayoutManager(requireContext())



        binding.imgTasbihClose.setOnClickListener {
            findNavController().navigate(R.id.boardFragment)

        }
        binding.imgAddNewTasbih.setOnClickListener {
            findNavController().navigate(R.id.addOwnTasbihFragment)
        }

        fetchDataFromFirebase()
    }
    private fun showDeleteConfirmationDialog(item: ZhikrTasbih, position: Int) {
        AlertDialog.Builder(requireContext())
            .setTitle("Delete Confirmation")
            .setMessage("Are you sure you want to delete this item?")
            .setPositiveButton("Yes") { dialog, which ->
                deleteItem(item, position)
            }
            .setNegativeButton("No", null)
            .show()
    }

    private fun deleteItem(item: ZhikrTasbih, position: Int) {
        if (item.zhikrName == "t1" || item.zhikrName == "t2") {
            Toast.makeText(requireContext(), "This item cannot be deleted", Toast.LENGTH_SHORT).show()
            return
        }

        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        val itemRef = databaseReference.child(userId).child(item.key.toString())

        // Remove value from Firebase Realtime Database
        itemRef.removeValue().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d("TasbihFragment", "Data removed from Realtime Database successfully")
                // Remove associated image from Firebase Storage
                val storageRef = FirebaseStorage.getInstance().getReferenceFromUrl(item.zhikrImageUrl.toString())
                storageRef.delete().addOnCompleteListener { deleteTask ->
                    if (deleteTask.isSuccessful) {
                        Toast.makeText(requireContext(), "Item deleted successfully", Toast.LENGTH_SHORT).show()
                        // Remove the item from the adapter and notify the change
                        zhikrTasbihArrayList.removeAt(position)
                        adapter.notifyItemRemoved(position)
                    } else {
                        Toast.makeText(requireContext(), "Failed to delete image", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Log.e("TasbihFragment", "Failed to remove data from Realtime Database: ${task.exception?.message}")
                Toast.makeText(requireContext(), "Failed to delete item", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun fetchDataFromFirebase() {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        val userReference = databaseReference.child(userId)

        userReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                zhikrTasbihArrayList.clear()
                for (zhikrSnapshot in snapshot.children) {
                    val key = zhikrSnapshot.key ?: continue
                    val zhikrName = zhikrSnapshot.child("zhikrName").getValue(String::class.java) ?: continue
                    val zhikrImageUrl = zhikrSnapshot.child("zhikrImageUrl").getValue(String::class.java) ?: continue

                    val zhikrItem = ZhikrTasbih(zhikrName, zhikrImageUrl,key )
                    zhikrTasbihArrayList.add(zhikrItem)
                }
               recyclerView.adapter = adapter
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e(TasbihFragment::class.java.simpleName, "Database error: ${error.message}")
            }
        })
    }

//    private fun fetchDataFromFirebase2() {
//        val userId = FirebaseAuth.getInstance().currentUser?.uid
//        val guestDataRef = FirebaseDatabase.getInstance().getReference("ZhikrTasbih")
//
//        val valueEventListener = object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                val zhikrTasbihArrayList = mutableListOf<ZhikrTasbih>()
//
//                // If user is logged in, fetch and add their data
//                userId?.let { uid ->
//                    val userSnapshot = snapshot.child(uid)
//                    for (zhikrSnapshot in userSnapshot.children) {
//                        val zhikrName = zhikrSnapshot.child("zhikrName").getValue(String::class.java)
//                        val imageUrl = zhikrSnapshot.child("zhikrImageUrl").getValue(String::class.java)
//                        zhikrName?.let { name ->
//                            imageUrl?.let { url ->
//                                zhikrTasbihArrayList.add(ZhikrTasbih(name, url))
//                            }
//                        }
//                    }
//                }
//
//                // Update RecyclerView adapter with the fetched data
//                recyclerView.adapter = TasbihZhikrAdapter(mContext, zhikrTasbihArrayList)
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                Log.e(TasbihFragment::class.java.simpleName, "Database error: ${error.message}")
//            }
//        }
//
//        // Attach the listener to the guestDataRef
//        guestDataRef.addListenerForSingleValueEvent(valueEventListener)
//    }


//    private fun fetchDataFromFirebase() {
//        val userId = FirebaseAuth.getInstance().currentUser?.uid
//        val guestDataRef = FirebaseDatabase.getInstance().getReference("ZhikrTasbih")
//
//        val valueEventListener = object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                zhikrTasbihArrayList.clear()
//
//                for (zhikrSnapshot in snapshot.children) {
//                    val zhikrName = zhikrSnapshot.child("zhikrName").getValue(String::class.java)
//                    val imageUrl = zhikrSnapshot.child("zhikrImageUrl").getValue(String::class.java)
//
//                    // Ensure both zhikrName and imageUrl are not null
//                    if (zhikrName != null && imageUrl != null) {
//                        val zhikrItem = ZhikrTasbih(zhikrName, imageUrl)
//                        zhikrTasbihArrayList.add(zhikrItem)
//                    } else {
//                        Log.e(
//                            TasbihFragment::class.java.simpleName,
//                            "Null values encountered for zhikrName or imageUrl"
//                        )
//                    }
//                }
//
//                // Update RecyclerView adapter with the fetched data
//                recyclerView.adapter = TasbihZhikrAdapter(mContext, zhikrTasbihArrayList)
//
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                Log.e(
//                    TasbihFragment::class.java.simpleName,
//                    "Database error: ${error.message}"
//                )
//            }
//        }
//
//        if (userId != null) {
//            // User is logged in, fetch data for the logged-in user
//            val userReference = guestDataRef.child(userId)
//            userReference.addListenerForSingleValueEvent(valueEventListener)
//        } else {
//            // User is not logged in (guest user), fetch data for the guest user
//            guestDataRef.addListenerForSingleValueEvent(valueEventListener)
//        }
//    }


//    private fun fetchDataFromFirebase() {
//            val userId = FirebaseAuth.getInstance().currentUser?.uid
//            userId?.let { uid ->
//                val userReference = FirebaseDatabase.getInstance().getReference("ZhikrTasbih").child(uid)
//                userReference.addListenerForSingleValueEvent(object : ValueEventListener {
//                    override fun onDataChange(snapshot: DataSnapshot) {
//                        for (zhikrSnapshot in snapshot.children) {
//                            val zhikrName = zhikrSnapshot.child("name").getValue(String::class.java)
//                            val imageUrl = zhikrSnapshot.child("imageUrl").getValue(String::class.java)
//                            Log.d(
//                                TasbihFragment::class.java.simpleName,
//                                "Zhikr name: $zhikrName, Image URL: $imageUrl"
//                            )
//                            val zhikrItem = ZhikrTasbih(zhikrName, imageUrl)
//                            zhikrTasbihArrayList.add(zhikrItem)
//                            // Update UI or perform other actions with the fetched data
//
//                        }
//                        recyclerView.adapter = adapter
//                    }
//
//                    override fun onCancelled(error: DatabaseError) {
//                        Log.e(
//                            TasbihFragment::class.java.simpleName,
//                            "Database error: ${error.message}"
//                        )
//                    }
//                })
//            } ?: run {
//                Log.e(
//                   TasbihFragment::class.java.simpleName,
//                    "User is not logged in"
//                )
//            }
//
//        }


//    private fun loadGuestData() {
//
//        val guestDataRef = databaseReference.child("private")
//        guestDataRef.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                zhikrTasbihArrayList.clear()
//                for (childSnapshot in snapshot.children) {
//                    val zhikrTasbih = childSnapshot.getValue(ZhikrTasbih::class.java)
//                    zhikrTasbih?.let {
//                        // Add guest data to the list
//                        zhikrTasbihArrayList.add(it)
//                    }
//                }
//                recyclerView.adapter = adapter
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                // Handle error
//            }
//        })
//    }
//
//    private fun loadUserData(userId: String) {
//        //   FirebaseDatabase.getInstance().getReference("users").child(userId)
//        val userReference = databaseReference.child("user").child(userId)
//        userReference.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                zhikrTasbihArrayList.clear()
//                for (childSnapshot in snapshot.children) {
//                    val zhikrTasbih = childSnapshot.getValue(ZhikrTasbih::class.java)
//                    zhikrTasbih?.let {
//                        // Add user-specific data to the list
//                        zhikrTasbihArrayList.add(it)
//                    }
//                }
//                recyclerView.adapter = adapter
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//
//                // Handle error
//            }
//        })
//    }
}


//    private fun fetchDataFromFirebase() {
//        val currentUser = FirebaseAuth.getInstance().currentUser
//
//
//        val valueEventListener = object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                zhikrTasbihArrayList.clear()
//                for (childSnapshot in snapshot.children) {
//                    val zhikrTasbih = childSnapshot.getValue(ZhikrTasbih::class.java)
//                    zhikrTasbih?.let {
//                        // Add data to the list
//                        zhikrTasbihArrayList.add(it)
//                    }
//                }
//                recyclerView.adapter = adapter
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                // Handle error
//            }
//        }
//
//        if (currentUser == null) {
//            // Guest user, load data from the "guest" node
//            val guestReference = databaseReference.child("private")
//            guestReference.addValueEventListener(valueEventListener)
//        } else {
//            // Logged-in user, load data from the "user" node based on their user ID
//            val userId = currentUser.uid
//            val userReference = databaseReference.child("user")
//            userReference.addValueEventListener(valueEventListener)
//        }
//    }

//    private fun fetchDataFromFirebase() {
//        // Show loading indicator
//      //  binding.loadingIndicator.visibility = View.VISIBLE
//
//        databaseReference.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                zhikrTasbihArrayList.clear()
//                val currentUser = FirebaseAuth.getInstance().currentUser
//                for (childSnapshot in snapshot.children) {
//                    val zhikrTasbih = childSnapshot.getValue(ZhikrTasbih::class.java)
//                    zhikrTasbih?.let {
//                        // Add data that is not associated with any user ID or added manually
//                        zhikrTasbihArrayList.add(it)
//                    }
//                }
//                recyclerView.adapter = adapter
//
//            }
//
//
//            override fun onCancelled(error: DatabaseError) {
//                // Handle error
//                // Hide loading indicator in case of error
//                binding.loadingIndicator.visibility = View.GONE
//            }
//        })
//    }


//                        // Check if the current user is logged in or not
//                        if (currentUser != null) {
//                            // User is logged in
//                            if (childSnapshot.key == null || childSnapshot.key == currentUser.uid) {
//                                // Add data that is manually added or associated with the user's ID
//                                tempList.add(it)
//                            }
//                        } else {
//                            // User is not logged in (guest user)
//                            if (childSnapshot.key == null) {
//                                // Add data that is manually added
//                                tempList.add(it)
//                            }
//                        }
//                    }
//                }
//                // Update RecyclerView adapter with the loaded data
//                zhikrTasbihArrayList.clear()
//                zhikrTasbihArrayList.addAll(tempList)
//                recyclerView.adapter?.notifyDataSetChanged()
//
//                // Hide loading indicator once data is loaded
//               binding.loadingIndicator.visibility = View.GONE
//}





