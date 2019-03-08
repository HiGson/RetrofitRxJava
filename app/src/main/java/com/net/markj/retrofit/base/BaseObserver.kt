package com.xbxm.jingxuan.services.util.http

import com.net.markj.retrofit.base.BaseModel
import com.net.markj.retrofit.http.httpExceptionConvertString
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

/**
 * Created by Kron Xu  on 2018/7/2
 */
abstract class BaseObserver<T>: Observer<T> {

    /**
     * 处理除了cookie失效以外所有的业务参数错误返回的结果
     * @throws Exception
     */
    @Throws(Exception::class)
    protected abstract fun onRequestError(message: String,code:Int)

    /**
     * 返回成功
     * @param t
     */
    protected abstract fun onSuccess(t: T)

    /**
     * 返回失败
     *
     * @param e
     * @throws Exception
     */
    @Throws(Exception::class)
    protected abstract fun onFailure(reson:String?,e: Throwable)


    protected abstract fun onRequestStart(d: Disposable)

    protected fun onRequestEnd() {

    }

    override fun onSubscribe(d: Disposable) {
        onRequestStart(d)
    }

    override fun onNext(t: T) {
        //TODO: 等接口定义下来以后根据接口格式判断请求数据，正确；回调 onSuccess，错误回调 onRequestError
        if (t is BaseModel<*>) {
            if (t.errorCode == 0) onSuccess(t)
            else onRequestError(t.message,t.errorCode)
        }
        onRequestEnd()
    }

    override fun onError(e: Throwable) {
        onRequestEnd()
        try {
            val reson = httpExceptionConvertString(e)
            onFailure(reson,e)
        } catch (e1: Exception) {
            e1.printStackTrace()
        }
    }

    override fun onComplete() {

    }

    //    public void showProgressDialog() {
    //        ProgressDialog.show(mContext, false, "请稍后");
    //    }
    //
    //    public void closeProgressDialog() {
    //        ProgressDialog.cancle();
    //    }

}