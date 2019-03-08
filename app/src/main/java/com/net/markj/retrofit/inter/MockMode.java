package com.net.markj.retrofit.inter;

/**
 * Created by Kron Xu on 2018/7/2
 */
public interface MockMode<T> {
    String mockJson();
    T getMock();
}
