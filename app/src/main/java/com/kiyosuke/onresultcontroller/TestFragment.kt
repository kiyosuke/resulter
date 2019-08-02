package com.kiyosuke.onresultcontroller


import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.kiyosuke.resulter.*
import com.kiyosuke.resulter.permissionWithResult
import kotlinx.android.synthetic.main.fragment_test.*


class TestFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_test, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        buttonPermission.setOnClickListener {
            permissionWithResult(
                arrayOf(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.ACCESS_FINE_LOCATION
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

        buttonResult.setOnClickListener {
            val i = Intent(requireContext(), RequestActivity::class.java)
            startActivityWithResult(i) { result ->
                result.onOk {
                    val data = it?.getStringExtra("result")
                    toast("Result.Ok, data=$data")
                }.onCancel {
                    toast("Result.Cancel")
                }
            }
        }
    }
}

private fun Fragment.toast(text: String) {
    context?.let {
        Toast.makeText(it, text, Toast.LENGTH_SHORT).show()
    }
}
