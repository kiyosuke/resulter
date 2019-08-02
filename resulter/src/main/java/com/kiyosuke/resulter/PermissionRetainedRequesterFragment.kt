package com.kiyosuke.resulter

import android.content.pm.PackageManager
import androidx.core.content.ContextCompat

/**
 * パーミッションリクエストを実行するFragment
 */
internal class PermissionRetainedRequesterFragment : RetainedRequesterFragment<PermissionResult>() {

    fun requestPermissions(permissions: Array<String>, callback: ResultCallback<PermissionResult>) {
        val requestCode = addRequest(callback)
        if (checkPermissions(permissions)) {
            dispatchResult(Result.Ok(PermissionResult.Granted), requestCode)
            return
        }
        requestPermissions(permissions, requestCode)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (hasRequestCode(requestCode)) {
            val result = handleResult(permissions, grantResults)
            dispatchResult(Result.Ok(result), requestCode)
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    private fun handleResult(permissions: Array<out String>, grantResults: IntArray): PermissionResult {
        val isAllGranted = grantResults.all { result -> result == PackageManager.PERMISSION_GRANTED }
        return when {
            isAllGranted -> PermissionResult.Granted
            checkNeverShowAgain(permissions) -> PermissionResult.NeverShowAgain
            else -> PermissionResult.Denied
        }
    }

    /**
     * パーミッションがすでにあるか確認する
     */
    private fun checkPermissions(permissions: Array<String>): Boolean =
        permissions
            .map { ContextCompat.checkSelfPermission(requireContext(), it) }
            .all { result -> result == PackageManager.PERMISSION_GRANTED }

    /**
     * 2回目以降にパーミッションダイアログを表示する際に今後表示しないチェックボックスが表示される
     * これが選択された状態なのかを確認する
     */
    private fun checkNeverShowAgain(permissions: Array<out String>): Boolean =
        permissions
            .map { shouldShowRequestPermissionRationale(it) }
            .all { !it }

    companion object {
        const val TAG = "__PERMISSION_RETAINED_REQUEST_FRAGMENT__"
    }
}