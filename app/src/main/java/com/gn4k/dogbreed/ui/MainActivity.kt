package com.gn4k.dogbreed.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Adapter
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gn4k.dogbreed.R
import com.gn4k.dogbreed.adapter.BreedAdapter
import com.gn4k.dogbreed.api.RetrofitClient
import com.gn4k.dogbreed.dataClass.DogApiResponse
import com.google.android.material.progressindicator.LinearProgressIndicator
import retrofit2.Call
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    lateinit var mergedList: List<String>
    lateinit var breedAdapter1 : BreedAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val proBar = findViewById<LinearProgressIndicator>(R.id.progressbar )
        val recycleLayoutGn = findViewById<RecyclerView>(R.id.name_list)
        recycleLayoutGn.layoutManager = LinearLayoutManager(this)


        val dogApi = RetrofitClient.dogApi
        val call = dogApi.getHoundImages()

        call.enqueue(object : retrofit2.Callback<DogApiResponse> {
            override fun onResponse(call: Call<DogApiResponse>, response: Response<DogApiResponse>) {
                if (response.isSuccessful) {
                    val dogApiResponse = response.body()
                    val imageUrls = dogApiResponse?.message ?: emptyList()

                    // Now you can use the imageUrls list as needed
                    // For example, you can display the images in an ImageView
                    if (imageUrls.isNotEmpty()) {
                        // Load the image using your preferred image loading library (e.g., Glide, Picasso)
                        //
                        val breedNames = mutableListOf<String>()

                        for (imageUrl in imageUrls) {
                            // Extract breed name from URL
                            val breedName = imageUrl.split("/").let {
                                if (it.size >= 6) {
                                    it[4] // Assuming the breed name is present at index 4
                                } else {
                                    ""
                                }
                            }

                            breedNames.add(breedName)
                        }


                        mergedList = mergeDuplicatesSet(breedNames)

                        val breedAdapter = BreedAdapter(mergedList, imageUrls, this@MainActivity)
                        breedAdapter1 = breedAdapter
                        recycleLayoutGn.adapter = breedAdapter
                        proBar.visibility = View.GONE

                    }
                }
            }

            override fun onFailure(call: Call<DogApiResponse>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Something went wrong", Toast.LENGTH_SHORT).show()
            }
        })

        val searchEditText = findViewById<EditText>(R.id.editTextText)
        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                s?.let { query ->
                    filterList(query.toString())
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })


    }

    fun mergeDuplicatesSet(list: List<String>): List<String> {
        val uniqueList = list.toSet().toList()
        return uniqueList
    }

    fun filterList(query: String) {
        val adapter = breedAdapter1
        if (adapter != null) {
            val filteredList = mergedList.filter { breedName ->
                breedName.contains(query, ignoreCase = true)
            }
            adapter.setBreedList(filteredList)
        }
    }


}
