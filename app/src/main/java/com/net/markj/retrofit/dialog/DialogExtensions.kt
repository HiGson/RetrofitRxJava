package com.net.markj.retrofit.dialog

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.support.v7.app.AlertDialog
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import com.net.markj.retrofit.R

/**
 * Created by Kron Xu  on 2018/7/2
 */
inline fun Context.showMessageDialog(message: String, isFinishActivity: Boolean = false): AlertDialog {
    return this.showMessageDialog(message, isFinishActivity, {})
}

inline fun Context.showMessageDialog(message: String, isFinishActivity: Boolean = false, crossinline closeListener: (ctx: Context) -> Unit): AlertDialog {

    val close = { it: DialogInterface ->
        closeListener(this)

        it.dismiss()
        if (isFinishActivity) {
            (this as Activity).finish()
        }
    }

    var dialog = AlertDialog.Builder(this)
            .setMessage(message)
            .setPositiveButton("确定") { d, _ -> close(d!!) }
            .setOnCancelListener(close)
            .show()


    return dialog
}

inline fun Context.createProgressDialog(message: String? = null, isTransparentBg: Boolean = false, cancleAble:Boolean): AlertDialog {

    var view = LayoutInflater.from(this).inflate(R.layout.progress_dialog_custom, null)
    var progressBar = view.findViewById<ProgressBar>(R.id.progressBar)

    var dialog = AlertDialog.Builder(this)
            .setView(view)
            .setCancelable(cancleAble)
            .create()


    progressBar.indeterminateColor(Color.parseColor("#cccccc"))

    if (!TextUtils.isEmpty(message)) {
        var textView = view.findViewById<TextView>(R.id.message)
        textView.text = message

        textView.visibility = View.VISIBLE
    }


    if (isTransparentBg) {
        dialog.window.attributes.dimAmount = 0f
    }

    dialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    dialog.window.setWindowAnimations(R.style.DialogFadeCenterInOutAnim)

    return dialog
}

inline fun ProgressBar.indeterminateColor(color: Int) {


    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        this.indeterminateTintList = ColorStateList.valueOf(color)
    } else {
        var drawable = this.indeterminateDrawable.mutate()
        drawable.setColorFilter(color, PorterDuff.Mode.SRC_IN)
        this.indeterminateDrawable = drawable
    }
}