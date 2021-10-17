package ru.skillbranch.gameofthrones.ui

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.skillbranch.gameofthrones.R
import ru.skillbranch.gameofthrones.data.remote.res.CharacterRes
import ru.skillbranch.gameofthrones.network.Loader
import ru.skillbranch.gameofthrones.network.NetworkService

class RootActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_root)
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