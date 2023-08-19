package com.gn4k.dogbreed.adapter
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.gn4k.dogbreed.R
import com.gn4k.dogbreed.ui.ShowCollag

class BreedAdapter(private var breedNames: List<String>, private val urlList: List<String>, private val context: Context) :
    RecyclerView.Adapter<BreedViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BreedViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.breed_list, parent, false)
        return BreedViewHolder(view)
    }

    fun setBreedList(list: List<String>) {
        breedNames = list
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: BreedViewHolder, position: Int) {
        val breedName = breedNames[position]
        holder.breedNameTextView.text = formatBreedName(breedName)

        val filteredBreedNames = urlList.filter { it.contains(breedName, ignoreCase = true) }

        Glide.with(holder.itemView.context)
            .load(filteredBreedNames[1])
            .placeholder(R.drawable.placeholder_img)
            .into(holder.img)

        holder.card.setOnClickListener {
            val intent = Intent(context, ShowCollag::class.java)
            intent.putExtra("breedName", formatBreedName(breedName))
            intent.putStringArrayListExtra("list", ArrayList(filteredBreedNames)) // Convert list to ArrayList if needed
            context.startActivity(intent)
        }


    }

    override fun getItemCount(): Int {
        return breedNames.size
    }

    fun formatBreedName(breedName: String): String {
        val words = breedName.split("-")
        val formattedWords = words.map { it.capitalize() }
        return formattedWords.joinToString(" ")
    }

}


class BreedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val breedNameTextView: TextView = itemView.findViewById(R.id.breed_name)
    val img: ImageView = itemView.findViewById(R.id.dog_img)
    val card: CardView = itemView.findViewById(R.id.cardView)

}