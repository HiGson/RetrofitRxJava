package com.net.markj.retrofit.http

import android.app.Activity
import android.app.Dialog
import android.content.Context
import com.net.markj.retrofit.dialog.createProgressDialog
import com.net.markj.retrofit.util.shortT
import java.lang.ref.WeakReference

/**
 * Created by Kron Xu on 2018/7/2
 */
abstract class HttpDialogCallback<T>(val context: Context, val isShowDialog: Boolean, var mProgressDialog: Dialog? = null, val cancleAble: Boolean = false) : HttpCallback<T>() {
    var mActivity: WeakReference<Activity>? = null

    constructor(context: Context, cancleAble: Boolean = false) : this(context, true, cancleAble = cancleAble)
    constructor(context: Context, cancleAble: Boolean = false, mProgressDialog: Dialog? = null) : this(context, true, mProgressDialog, cancleAble)

    init {
        if (context is Activity) mActivity = WeakReference(context)
    }


    override fun onBefore() {
        if (isShowDialog) {
            mProgressDialog = mActivity?.get()?.createProgressDialog(cancleAble = cancleAble)
            mProgressDialog?.show()
        }
    }

    override fun onFailure(msg: String?) {
        mProgressDialog?.dismiss()
        msg?.also {
            context.shortT(msg)
        }
        failure(msg)
    }

    override fun onRequestError(message: String, code: Int) {
        mProgressDialog?.dismiss()
        requestError(message, code)
    }

    override fun onSuccess(t: T) {
        mProgressDialog?.dismiss()
        success(t)
    }

    abstract fun success(t: T)
    abstract fun failure(msg: String?)
    abstract fun requestError(message: String, code: Int)
}