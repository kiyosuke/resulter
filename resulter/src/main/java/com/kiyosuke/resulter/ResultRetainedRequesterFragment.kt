package com.kiyosuke.resulter

import android.app.Activity
import android.content.Intent

/**
 * startActivityForResultを行うFragment
 */
internal class ResultRetainedRequesterFragment : RetainedRequesterFragment<Intent?>() {

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (resultCode) {
            Activity.RESULT_OK -> dispatchResult(Result.Ok(data), requestCode)
            Activity.RESULT_CANCELED -> dispatchResult(Result.Cancel, requestCode)
            else -> super.onActivityResult(requestCode, resultCode, data)
        }
    }

    companion object {
        const val TAG = "__RETAINED_RESULT_REQUESTER_FRAGMENT__"
    }
}