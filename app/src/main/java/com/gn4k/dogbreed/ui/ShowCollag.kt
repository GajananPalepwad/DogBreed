package com.gn4k.dogbreed.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gn4k.dogbreed.R
import com.gn4k.dogbreed.adapter.ImageAdapter

class ShowCollag : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_collag)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val breedName = findViewById<TextView>(R.id.breed_name)
        val btnBack = findViewById<CardView>(R.id.back)


        val formattedBreedName = intent.getStringExtra("breedName")
        val receivedList = intent.getStringArrayListExtra("list")

        breedName.text = formattedBreedName

        btnBack.setOnClickListener { onBackPressed() }

        if (receivedList != null) {
            val adapter = ImageAdapter(receivedList, this)
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(this)
        }

    }
}