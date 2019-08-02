package com.kiyosuke.resulter

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity

/**
 * 別Activityからのデータ返却時のコールバック処理を関数呼び出し部分で定義する
 */
fun AppCompatActivity.startActivityWithResult(intent: Intent, onResult: (result: Result<Intent?>) -> Unit) {
    var requesterFragment = findResultRetainedFragment()
    val requireAddFragment = requesterFragment == null
    if (requesterFragment == null) {
        requesterFragment = ResultRetainedRequesterFragment()
    }

    val requestCode = requesterFragment.addRequest(object : OnResultListener {
        override fun onResult(result: Result<Intent?>) {
            onResult(result)
        }
    })

    if (requireAddFragment) {
        supportFragmentManager
            .beginTransaction()
            .add(requesterFragment, ResultRetainedRequesterFragment.TAG)
            .commit()
    }
    supportFragmentManager.executePendingTransactions()
    requesterFragment.startActivityForResult(intent, requestCode)
}

/**
 * パーミッションリクエストを行い結果のコールバックを関数呼び出し部分で定義する
 */
fun AppCompatActivity.permissionWithResult(
    permissions: Array<String>,
    onResult: (result: Result<PermissionResult>) -> Unit
) {
    var requesterFragment = findPermissionRetainedFragment()
    val requireAddFragment = requesterFragment == null
    if (requesterFragment == null) {
        requesterFragment = PermissionRetainedRequesterFragment()
    }

    if (requireAddFragment) {
        supportFragmentManager
            .beginTransaction()
            .add(requesterFragment, PermissionRetainedRequesterFragment.TAG)
            .commit()

        supportFragmentManager.executePendingTransactions()
        requesterFragment.requestPermissions(permissions, object : OnPermissionResultListener {
            override fun onResult(result: Result<PermissionResult>) {
                onResult(result)
            }
        })
    }
}

private fun AppCompatActivity.findResultRetainedFragment(): ResultRetainedRequesterFragment? =
    supportFragmentManager.findFragmentByTag(ResultRetainedRequesterFragment.TAG) as? ResultRetainedRequesterFragment

private fun AppCompatActivity.findPermissionRetainedFragment(): PermissionRetainedRequesterFragment? =
    supportFragmentManager.findFragmentByTag(PermissionRetainedRequesterFragment.TAG) as? PermissionRetainedRequesterFragment
