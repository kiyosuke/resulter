package com.kiyosuke.resulter

import android.content.Intent
import androidx.fragment.app.Fragment

fun Fragment.startActivityWithResult(intent: Intent, onResult: (result: Result<Intent?>) -> Unit) {
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
        childFragmentManager
            .beginTransaction()
            .add(requesterFragment, ResultRetainedRequesterFragment.TAG)
            .commit()
    }
    childFragmentManager.executePendingTransactions()
    requesterFragment.startActivityForResult(intent, requestCode)
}

fun Fragment.permissionWithResult(permissions: Array<String>, onResult: (result: Result<PermissionResult>) -> Unit) {
    var requesterFragment = findPermissionRetainedFragment()
    val requireAddFragment = requesterFragment == null
    if (requesterFragment == null) {
        requesterFragment = PermissionRetainedRequesterFragment()
    }

    if (requireAddFragment) {
        childFragmentManager
            .beginTransaction()
            .add(requesterFragment, PermissionRetainedRequesterFragment.TAG)
            .commit()

        childFragmentManager.executePendingTransactions()
        requesterFragment.requestPermissions(permissions, object : OnPermissionResultListener {
            override fun onResult(result: Result<PermissionResult>) {
                onResult(result)
            }
        })
    }
}

private fun Fragment.findResultRetainedFragment(): ResultRetainedRequesterFragment? =
    childFragmentManager.findFragmentByTag(ResultRetainedRequesterFragment.TAG) as? ResultRetainedRequesterFragment

private fun Fragment.findPermissionRetainedFragment(): PermissionRetainedRequesterFragment? =
    childFragmentManager.findFragmentByTag(PermissionRetainedRequesterFragment.TAG) as? PermissionRetainedRequesterFragment
