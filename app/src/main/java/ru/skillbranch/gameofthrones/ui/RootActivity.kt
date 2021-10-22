package ru.skillbranch.gameofthrones.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.skillbranch.gameofthrones.R
import ru.skillbranch.gameofthrones.data.local.entities.House
import ru.skillbranch.gameofthrones.db.AppDatabase
import ru.skillbranch.gameofthrones.repositories.RootRepository

class RootActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_root)

//        val db = Room.databaseBuilder(
//            applicationContext,
//            AppDatabase::class.java,
//            "populus-database"
//        ).build()
//
//        val house = House(
//            id = "2",
//            name = "Name",
//            region = "Region",
//            coatOfArms = "",
//            words = "",
//            titles = "",
//            seats = "",
//            currentLord = "Vasia", //rel
//            heir = "2", //rel
//            overlord = "",
//            founded = "",
//            founder = "", //rel
//            diedOut = "",
//            ancestralWeapons = ""
//        )
//
//        CoroutineScope(Dispatchers.IO).launch {
//            //db.getHouseDao().insertAll(house)
//            val houseFromDb = db.getHouseDao().getAllHouses()
//            print(houseFromDb)
//        }

        RootRepository.getAllHouses() {
            print(it)
        }

    }

    override fun onStart() {
        super.onStart()
//
//        val textView = findViewById<TextView>(R.id.textView)
//
//        Loader.isFinished.observe(this, {
//            textView.text = "Data loaded!"
//        })
//        Loader.load()


    }


}