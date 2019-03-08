package com.xbxm.jingxuan.services.util.http

import android.content.Context
import android.view.View
import android.widget.ProgressBar
import com.net.markj.retrofit.http.HttpCallback
import com.net.markj.retrofit.util.shortT

abstract class UploadProgressCallback<T>(val context: Context, val progressBar: ProgressBar) : HttpCallback<T>() {
    override fun onSuccess(t: T) {
        progressBar.visibility = View.GONE
        success(t)
    }

    override fun onRequestError(message: String, code: Int) {
        progressBar.visibility = View.GONE
        requestError(message, code)
    }

    override fun onFailure(msg: String?) {
        progressBar.visibility = View.GONE
        msg?.also {
            context.shortT(msg)
        }
        failure(msg)
    }

    override fun onBefore() {
        super.onBefore()
        progressBar.visibility = View.VISIBLE
    }

    abstract fun success(t: T)
    abstract fun failure(msg: String?)
    abstract fun requestError(message: String, code: Int)
}