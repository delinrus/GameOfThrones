package ru.skillbranch.gameofthrones.ui.houses.house

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.skillbranch.gameofthrones.R

class CharactersListAdapter : RecyclerView.Adapter<CharactersListAdapter.CharacterViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.character_list_item,
            parent,
            false
        )
        return CharacterViewHolder(view)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.tvCharacterDesctiption.text = "Great Lord of winterfell"
        holder.tvCharacterName.text = "Dany Targarian"
        holder.ivHouseLogo.setImageResource(R.drawable.targaryen_icon)
    }

    override fun getItemCount(): Int {
        return 10
    }

    class CharacterViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val ivHouseLogo = view.findViewById<ImageView>(R.id.house_logo)
        val tvCharacterName = view.findViewById<TextView>(R.id.character_name)
        val tvCharacterDesctiption = view.findViewById<TextView>(R.id.character_description)
    }
}