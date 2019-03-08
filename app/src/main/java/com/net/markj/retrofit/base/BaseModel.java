package com.net.markj.retrofit.base;

import com.net.markj.retrofit.inter.MockMode;

/**
 * Created by Kron Xu  on 2018/5/15
 */

public abstract class BaseModel<T> implements MockMode<T> {
    private String message;
    private int errorCode;

    final public int getErrorCode() {
        return errorCode;
    }

    final public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    final public String getMessage() {
        return message;
    }

    final public void setMessage(String message) {
        this.message = message;
    }

}
