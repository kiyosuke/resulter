package com.kiyosuke.onresultcontroller

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class FragmentTestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment_test)

        val frag = TestFragment()
        supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, frag).commit()
    }
}
