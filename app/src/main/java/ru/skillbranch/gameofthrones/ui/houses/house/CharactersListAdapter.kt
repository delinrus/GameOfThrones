package ru.skillbranch.gameofthrones.ui.houses.house

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.skillbranch.gameofthrones.data.local.entities.CharacterItem
import ru.skillbranch.gameofthrones.data.local.entities.HouseType
import ru.skillbranch.gameofthrones.databinding.CharacterListItemBinding

class CharactersListAdapter(val houseType: HouseType) :
    RecyclerView.Adapter<CharactersListAdapter.CharacterViewHolder>() {

    var onCharacterItemClickListener: ((CharacterItem) -> Unit)? = null

    var characters = listOf<CharacterItem>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val binding = CharacterListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CharacterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.binding.apply {
            val character = characters[position]

            val akaList = character.titles + character.aliases
            characterDescription.text = akaList.joinToString(separator = " • ") { it }

            val name = character.name
            characterName.text = if (name.isNotBlank()) name else "Information is unknown"

            houseLogo.setImageResource(houseType.imageRes)
        }

        holder.binding.root.setOnClickListener {
            onCharacterItemClickListener?.invoke(characters[position])
        }
    }

    override fun getItemCount(): Int {
        return characters.size
    }

    class CharacterViewHolder(
        val binding: CharacterListItemBinding
    ) : RecyclerView.ViewHolder(binding.root)
}