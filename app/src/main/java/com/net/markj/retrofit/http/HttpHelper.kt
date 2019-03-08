package com.net.markj.retrofit.http

import com.google.gson.GsonBuilder
import com.net.markj.retrofit.BuildConfig
import com.net.markj.retrofit.apis.Apis
import com.net.markj.retrofit.app.App
import com.net.markj.retrofit.inter.MockMode
import com.xbxm.jingxuan.services.util.http.BaseObserver
import com.xbxm.retrofiturlmanager.RetrofitUrlManager
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import org.json.JSONArray
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Created by Kron Xu on 2018/7/2
 */
class HttpHelper private constructor() {
    private object Holder {
        val INSTANCE = HttpHelper()
    }

    companion object {
        val instance: HttpHelper by lazy { Holder.INSTANCE }
        fun getParams() = Params()
    }

    var retrofitApis: Apis? = null
    fun Init() {
        val gson = GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").setLenient().create()
        // Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
//        val cacheFile = File(context, "cache")
        // 10M缓存
//        val cache = Cache(cacheFile, (1024 * 1024 * 10).toLong())

        val httpClient = RetrofitUrlManager.getInstance()
                .with(OkHttpClient.Builder())
                .readTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS)
                .build()

        val mRetrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        retrofitApis = mRetrofit.create(Apis::class.java)

    }

    fun <T> request(observable: Observable<T>?, callback: HttpCallback<T>, isMock: Boolean): Disposable? {
        var disposable: Disposable? = null
        if (isMock && BuildConfig.IS_MOCK) {
            val t = callback.getT()
            if (t is MockMode<*>) {
                callback.onSuccess(t.mock as T)
            }
            return null
        } else {
            observable?.subscribeOn(Schedulers.io())
                    ?.observeOn(AndroidSchedulers.mainThread())
                    ?.subscribe(object : BaseObserver<T>() {
                        override fun onRequestError(message: String, code: Int) {

                        }

                        override fun onSuccess(t: T) {
                            callback.onSuccess(t)
                        }

                        override fun onFailure(reson: String?, e: Throwable) {
                            callback.onFailure(reson)
                        }

                        override fun onRequestStart(d: Disposable) {
                            callback.onBefore()
                            disposable = d
                        }

                    })
            return disposable
        }
    }

    class Params {
        val map: HashMap<String, String> = HashMap()

//        init {
//            map = HashMap()
//        }
//
//        companion object {
//            fun getParams(): Params {
//                return Params()
//            }
//        }

        fun put(key: String, value: Objects): Params {
            map?.put(key, value.toString())
            return this
        }

        fun put(key: String, value: Int): Params {
            map?.put(key, value.toString())
            return this
        }

        fun put(key: String, value: String): Params {
            map?.put(key, value.toString())
            return this
        }

        fun put(key: String, value: Float): Params {
            map?.put(key, value.toString())
            return this
        }

        fun put(key: String, value: Double): Params {
            map?.put(key, value.toString())
            return this
        }

        fun put(key: String, value: Long): Params {
            map?.put(key, value.toString())
            return this
        }

        fun put(key: String, value: Boolean): Params {
            map?.put(key, value.toString())
            return this
        }

        fun put(key: String, value: JSONArray): Params {
            map?.put(key, value.toString())
            return this
        }

    }
}