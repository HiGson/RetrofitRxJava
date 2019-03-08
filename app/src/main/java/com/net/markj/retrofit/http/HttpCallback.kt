package com.net.markj.retrofit.http

import com.google.gson.internal.`$Gson$Types`
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * Created by Kron Xu on 2018/7/2
 */
abstract class HttpCallback<T> {
    private var mType: Type? = null

    init {
        mType = getSuperclassTypeParameter(javaClass)
    }

    companion object {
        private val TYPE_NAME_PREFIX = "class "
        @Throws(ClassNotFoundException::class)
        fun getClass(type: Type): Class<*>? {
            val className = getClassName(type)
            return if (className == null || className.isEmpty()) {
                null
            } else Class.forName(className)
        }

        private fun getClassName(type: Type?): String {
            if (type == null) {
                return ""
            }
            var className = type.toString()
            if (className.startsWith(TYPE_NAME_PREFIX)) {
                className = className.substring(TYPE_NAME_PREFIX.length)
            }
            return className
        }

    }

    abstract fun onSuccess(t: T)

    abstract fun onRequestError(message: String,code:Int)

    abstract fun onFailure(msg: String?)
    open fun onBefore(){

    }

    internal fun getSuperclassTypeParameter(subclass: Class<*>?): Type? {
        val superclass = subclass!!.genericSuperclass
        if (superclass is Class<*>) {
            throw RuntimeException("Missing type parameter.")
        }
        val parameterized = superclass as ParameterizedType
        return `$Gson$Types`.canonicalize(parameterized.actualTypeArguments[0])
    }

    fun getT(): T? {
        try {
            val o = getClass(mType!!)?.newInstance()
            if (o != null) {
                return o as T
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return null
    }

}