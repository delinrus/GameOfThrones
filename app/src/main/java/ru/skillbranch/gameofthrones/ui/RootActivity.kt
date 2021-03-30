package ru.skillbranch.gameofthrones.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.skillbranch.gameofthrones.R
import ru.skillbranch.gameofthrones.network.NetworkService
import ru.skillbranch.gameofthrones.network.Post

class RootActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_root)

        val textView = findViewById<TextView>(R.id.textView)

        NetworkService.getJSONApi()
            .getPostWithID(1)
            .enqueue(object : Callback<Post> {
                override fun onResponse(call: Call<Post>, response: Response<Post>) {
                    val post = response.body()
                    post?.let {
                        textView.append("${it.id} \n")
                        textView.append("${it.userId} + \n")
                        textView.append("${it.title} + \n")
                        textView.append("${it.body} + \n")
                    }
                }

                override fun onFailure(call: Call<Post>, t: Throwable) {
                    textView.append("Error occurred while getting request!")
                    t.printStackTrace()
                }
            })

    }
}