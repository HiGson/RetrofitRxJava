package com.net.markj.retrofit.app

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import com.net.markj.retrofit.BuildConfig
import com.net.markj.retrofit.Config
import com.net.markj.retrofit.http.HttpHelper
import com.xbxm.retrofiturlmanager.L
import com.xbxm.retrofiturlmanager.RetrofitUrlManager

/**
 * Created by Kron Xu on 2018/7/2
 */
class App : Application() {

    companion object {
        private var context: App? = null
        fun appContext(): Context = context!!
    }
    override fun onCreate() {
        super.onCreate()
        context = this@App

        HttpHelper.instance.Init()
        RetrofitUrlManager.getInstance().isRun = true
        RetrofitUrlManager.getInstance().putDomain(Config.BASE_URL, BuildConfig.BASE_URL)
        RetrofitUrlManager.getInstance().putDomain(Config.BASE_URL_UPLOAD_PIC, BuildConfig.BASE_URL_UPLOAD_PIC)
    }

}