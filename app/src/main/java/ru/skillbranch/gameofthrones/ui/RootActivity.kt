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
import ru.skillbranch.gameofthrones.network.NetworkService

class RootActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_root)

        val textView = findViewById<TextView>(R.id.textView)

//        NetworkService.getWebServiceApi()
//            .getHouseByName(NEED_HOUSES[0])
//            .enqueue(object : Callback<List<HouseRes>> {
//                override fun onResponse(call: Call<List<HouseRes>>, response: Response<List<HouseRes>>) {
//                    val post = response.body()
//                    post?.let {
//                        textView.append("${it[0]}\n")
//                        Log.d("RootActivity",it[0].toString())
//                    }
//                }
//
//                override fun onFailure(call: Call<List<HouseRes>>, t: Throwable) {
//                    textView.append("Error occurred while getting request!")
//                    t.printStackTrace()
//                }
//            })

        NetworkService.getWebServiceApi()
            .getCharacterByID(2)
            .enqueue(object : Callback<CharacterRes> {
                override fun onResponse(
                    call: Call<CharacterRes>,
                    response: Response<CharacterRes>
                ) {
                    val post = response.body()
                    post?.let {
                        textView.append("${it}\n")
                        Log.d("RootActivity", it.toString())
                    }
                }

                override fun onFailure(call: Call<CharacterRes>, t: Throwable) {
                    textView.append("Error occurred while getting request!")
                    t.printStackTrace()
                }
            })


    }
}