package com.kiyosuke.resulter

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import kotlin.random.Random

internal abstract class RetainedRequesterFragment<T> : Fragment() {
    private val serializableCallbacks = hashMapOf<Int, ResultCallback<T>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    fun hasRequestCode(requestCode: Int): Boolean = serializableCallbacks.containsKey(requestCode)

    internal fun addRequest(callback: ResultCallback<T>): Int {
        val requestCode = createRequestCode()
        addRequest(requestCode, callback)
        return requestCode
    }

    internal fun addRequest(requestCode: Int, callback: ResultCallback<T>) {
        serializableCallbacks[requestCode] = callback
    }

    /**
     * ユニークなリクエストコードをランダム生成
     * リクエストコードは16bit以内じゃないとクラッシュする
     */
    private tailrec fun createRequestCode(): Int {
        val code = Random.nextInt(0, 65535)
        return if (serializableCallbacks.contains(code)) {
            createRequestCode()
        } else {
            code
        }
    }

    protected fun dispatchResult(result: Result<T>, requestCode: Int) {
        fragmentManager?.executePendingTransactions()
        val callback = serializableCallbacks[requestCode]
        callback?.onResult(result)
        serializableCallbacks.remove(requestCode)

        if (serializableCallbacks.isEmpty()) {
            fragmentManager?.beginTransaction()?.remove(this)?.commit()
        }
    }

    companion object {
        private const val KEY_CALLBACKS = "key_callbacks"
    }
}