package ru.skillbranch.gameofthrones.ui.character

import android.graphics.PorterDuff
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.lifecycle.MediatorLiveData
import androidx.navigation.fragment.navArgs
import ru.skillbranch.gameofthrones.data.local.entities.CharacterFull
import ru.skillbranch.gameofthrones.data.local.entities.HouseType
import ru.skillbranch.gameofthrones.databinding.FragmentCharacterBinding
import ru.skillbranch.gameofthrones.repositories.RootRepository
import java.util.*


class CharacterFragment : Fragment() {

    private val args by navArgs<CharacterFragmentArgs>()

    private var _binding: FragmentCharacterBinding? = null
    private val binding
        get() = _binding ?: throw RuntimeException("FragmentCharacterBinding == null")

    private val characterFull = MediatorLiveData<CharacterFull>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCharacterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).apply {
            setSupportActionBar(binding.toolbar)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        characterFull.observe(viewLifecycleOwner) {
            val houseType = HouseType.valueOf(it.house.toUpperCase(Locale.ROOT))
            val housePrimaryColor = houseType.colorRes

            binding.collapsingToolBar.title = it.name

            binding.collapsingToolBar.setContentScrimColor(resources.getColor(housePrimaryColor))

            binding.houseCoatOfArms.setImageResource(houseType.coatOfArmsRes)

            binding.description.decImg1.imageTintList =
                context?.getColorStateList(housePrimaryColor)
            binding.description.decImg2.imageTintList =
                context?.getColorStateList(housePrimaryColor)
            binding.description.decImg3.imageTintList =
                context?.getColorStateList(housePrimaryColor)
            binding.description.decImg4.imageTintList =
                context?.getColorStateList(housePrimaryColor)

            binding.description.words.text = it.words
            binding.description.born.text = it.born
            binding.description.titles.text = it.titles.joinToString(separator = "\n")
            binding.description.aliases.text = it.aliases.joinToString(separator = "\n")
            binding.description.btnFather.isGone = it.father == null
            binding.description.lblFather.isGone = it.father == null
            if (it.father != null) {
                binding.description.btnFather.text = it.father.name.toUpperCase(Locale.ROOT)
                binding.description.btnFather.background.setColorFilter(
                    resources.getColor(housePrimaryColor),
                    PorterDuff.Mode.MULTIPLY
                )
            }

            binding.description.btnMother.isGone = it.mother == null
            binding.description.lblMother.isGone = it.mother == null
            if (it.mother != null) {
                binding.description.btnMother.text = it.mother.name.toUpperCase(Locale.ROOT)
                binding.description.btnMother.background.setColorFilter(
                    resources.getColor(housePrimaryColor),
                    PorterDuff.Mode.MULTIPLY
                )
            }
            binding.description.btnMother.setBackgroundColor(
                context?.resources?.getColor(housePrimaryColor) ?: 0
            )
        }

        RootRepository.findCharacterFullById(args.characterItem.id) {
            characterFull.postValue(it)
        }
    }
}