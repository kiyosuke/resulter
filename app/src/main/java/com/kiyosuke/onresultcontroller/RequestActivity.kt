package com.kiyosuke.onresultcontroller

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_request.*

class RequestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_request)

        button.setOnClickListener {
            setResult(Activity.RESULT_CANCELED)
            finish()
        }

        button2.setOnClickListener {
            val data = Intent().apply {
                putExtra("result", "結果です")
            }
            setResult(Activity.RESULT_OK, data)
            finish()
        }
    }
}
