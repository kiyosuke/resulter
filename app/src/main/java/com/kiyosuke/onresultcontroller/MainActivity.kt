package com.kiyosuke.onresultcontroller

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.kiyosuke.resulter.*
import kotlinx.android.synthetic.main.activity_main.*
import java.io.Serializable

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonSend.setOnClickListener {
            val i = Intent(applicationContext, RequestActivity::class.java)
            startActivityWithResult(i) { result ->
                result.onOk {
                    Log.d("MainActivity", "onOk")
                    val data = it?.getStringExtra("result")
                    toast("Result.Ok, data=$data")
                }.onCancel {
                    //                        Log.d("MainActivity", "cancel")
                    toast("Result.Cancel")
                }
            }
        }

        buttonPermission.setOnClickListener {
            permissionWithResult(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
            ) { result ->
                result.onOk {
                    when (it) {
                        PermissionResult.Granted -> toast("Granted")
                        PermissionResult.Denied -> toast("Denied")
                        PermissionResult.NeverShowAgain -> toast("NeverShowAgain")
                    }
                }
            }
        }

        buttonFragment.setOnClickListener {
            val i = Intent(applicationContext, FragmentTestActivity::class.java)
            startActivity(i)
        }
    }

    private fun Activity.toast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }

}