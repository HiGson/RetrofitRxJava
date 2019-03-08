package com.net.markj.retrofit.http

import com.net.markj.retrofit.app.App
import com.net.markj.retrofit.util.NetworkUtils
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException

/**
 * Created by Kron Xu on 2018/7/2
 */
inline fun httpExceptionConvertString(e: Throwable?): String? {
    if (!NetworkUtils.hasNetwork(App.appContext())) {
        return "无网络链接，请检查网络"
    }

    if (e is ConnectException) {
        return "服务器连接失败"
    }

    if (e is SocketTimeoutException) {
        return "服务器连接超时"
    }

    if (e is IOException) {
        return "服务器连接异常"
    }

    if (e is NumberFormatException) {
        return "数据类型转换异常"
    }
    if (e !is ResException) {
        return "服务器连接异常"
    }

    return null
}