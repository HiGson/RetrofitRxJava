package com.net.markj.retrofit.util

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.ExifInterface
import android.net.Uri
import android.support.v4.app.Fragment
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.webkit.WebView
import android.widget.TextView
import android.widget.Toast
import com.net.markj.retrofit.R
import com.net.markj.retrofit.util.DisplayUtils.dp2px
import java.io.IOException
import java.util.regex.Pattern
import kotlin.collections.ArrayList

/**
 * Created by Kron Xu on 2018/7/2
 * 此kt文件专门用来创建定义扩展函数
 */

/** 单例Toast */
var sToast: Toast? = null

private fun Context.show(message: Any, length: Int = Toast.LENGTH_SHORT) {
    if (null == sToast) {
        sToast = Toast.makeText(applicationContext, message?.toString(), length)
        sToast!!.show()
    } else {
        sToast!!.setText(message?.toString())
        sToast!!.duration = length
        sToast!!.show()
    }
}

fun Context.toastCenter(msg: String?) {
    if (TextUtils.isEmpty(msg)) {
        return
    }

    val toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT)

    val textView = TextView(this)
    textView.setBackgroundResource(R.drawable.toast_bg)
    textView.setTextColor(Color.WHITE)

    val tb = dp2px(this, 12F)
    val lr = dp2px(this, 24F)

    textView.setPadding(lr, tb, lr, tb)

    textView.text = msg

    toast.view = textView

    toast.setGravity(Gravity.CENTER, 0, 0)

    toast.show()

}

/**
 * Fragment和Context中古纳与Toast对扩展函数
 */
fun Fragment.shortT(message: String) = this.context?.toastCenter(message)

fun Fragment.longT(message: String) = this.context?.show(message, Toast.LENGTH_LONG)

fun Context.shortT(message: String) = this.toastCenter(message)

fun Context.longT(message: String) = this.toastCenter(message)

/**
 * 保证T不为空
 */

inline fun <reified T> T?.notNull(t: T? = null): T = this ?: (t ?: T::class.java.newInstance())

/**
 * 保证List不为空
 */
fun <T> List<T>?.notNull(t: List<T>? = null): List<T> = this ?: (t ?: ArrayList())

/**
 * 判断list不为null并且size>0
 */
fun <T> List<T>?.isEmptyOrNull(t: List<T>? = null): Boolean = this == null || this.isEmpty()

fun <T> List<T>?.isNotEmptyOrNull(t: List<T>? = null): Boolean = !this.isEmptyOrNull()


/**
 * 保证String不为空处理""  和"   "的问题
 */

fun String?.notNullStr(str: String? = ""): String = if (!this.isNullOrBlank()) this!! else str!!


/**
 * 设置View可见性的方法
 */

fun Any.setViewVisibility(visibilityValue: Int, vararg views: View) {
    for (view in views)
        view.visibility = visibilityValue
}

/**
 * 为了防止内存泄漏
 *
 * @param windowManager
 */
fun setConfigCallback(windowManager: WindowManager?) {
    try {
        var field = WebView::class.java.getDeclaredField("mWebViewCore")
        field = field.type.getDeclaredField("mBrowserFrame")
        field = field.type.getDeclaredField("sConfigCallback")
        field.isAccessible = true
        val configCallback = field.get(null) ?: return

        field = field.type.getDeclaredField("mWindowManager")
        field.isAccessible = true
        field.set(configCallback, windowManager)
    } catch (e: Exception) {
    }
}

@SuppressLint("MissingPermission")
fun Context.callPhone(tel: String) {
    val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:" + tel))
    if (null != intent.resolveActivity(packageManager)) {
        startActivity(intent)
    }
}

fun Context.startCallPhoneUI(tel: String) {
    val intent = Intent(Intent.ACTION_DIAL)
    intent.data = Uri.parse("tel:$tel")
    startActivity(intent)
}

fun Context.readProtocolHtml(fileName: String): String? {
    try {
        val sb = StringBuffer()
        val input = this.assets.open(fileName)
        var len = -1
        val buf = ByteArray(1024 * 1024)
        len = input.read(buf)
        while (len != -1) {
            sb.append(String(buf, 0, len, Charsets.UTF_8))
            len = input.read(buf)
        }
        input.close()
        return sb.toString()
    } catch (e: IOException) {
        e.printStackTrace()
        return null
    }
}

//String中去除空格 换行符号 制表符号 回车
fun String.replaceBlank() = Pattern.compile("\\s*|\t|\r|\n").matcher(this).replaceAll("")!!

fun shouldRotate(imageFilePath: String): Boolean {
    val degree = getImageDegree(imageFilePath)
    return degree != 0 && degree != 180
}

fun getImageDegree(imageFilePath: String): Int {
    var degree = 0
    try {
        // 从指定路径下读取图片，并获取其EXIF信息
        val exifInterface = ExifInterface(imageFilePath);
        // 获取图片的旋转信息
        val orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_NORMAL);
        degree = when (orientation) {
            ExifInterface.ORIENTATION_ROTATE_90 -> 90
            ExifInterface.ORIENTATION_ROTATE_180 -> 180
            ExifInterface.ORIENTATION_ROTATE_270 -> 270
            else -> 0
        }
    } catch (e: IOException) {
        e.printStackTrace();
        val tag = ImageUtils::class.java.simpleName
    }
    return degree
}
