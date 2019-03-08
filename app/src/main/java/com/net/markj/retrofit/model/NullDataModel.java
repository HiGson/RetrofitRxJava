package com.net.markj.retrofit.model;

import com.net.markj.retrofit.base.BaseModel;
import com.net.markj.retrofit.util.JsonUtils;

/**
 * Created by Kron Xu on 2018/7/2
 */
public class NullDataModel extends BaseModel<NullDataModel> {
    private boolean success;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @Override
    public String mockJson() {

        return "{\n" +
                "    \"success\":true,\n" +
                "    \"errorCode\":0,\n" +
                "    \"message\":\"成功\",\n" +
                "    \"data\":null\n" +
                "}";
    }

    @Override
    public NullDataModel getMock() {
        return JsonUtils.fromJson(mockJson(), getClass());
    }
}
