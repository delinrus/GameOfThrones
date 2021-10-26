package ru.skillbranch.gameofthrones.ui.houses.house

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.skillbranch.gameofthrones.R
import ru.skillbranch.gameofthrones.data.local.entities.CharacterItem

class CharactersListAdapter : RecyclerView.Adapter<CharactersListAdapter.CharacterViewHolder>() {

    var characters = listOf<CharacterItem>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.character_list_item,
            parent,
            false
        )
        return CharacterViewHolder(view)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.tvCharacterDesctiption.text = characters[position].titles.toString()
        holder.tvCharacterName.text = characters[position].name
        holder.ivHouseLogo.setImageResource(R.drawable.targaryen_icon)
    }

    override fun getItemCount(): Int {
        return characters.size
    }

    class CharacterViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val ivHouseLogo = view.findViewById<ImageView>(R.id.house_logo)
        val tvCharacterName = view.findViewById<TextView>(R.id.character_name)
        val tvCharacterDesctiption = view.findViewById<TextView>(R.id.character_description)
    }
}