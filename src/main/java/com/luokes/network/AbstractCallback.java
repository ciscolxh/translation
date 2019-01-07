package com.luokes.network;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class AbstractCallback<T> {
    public Type mType;

    protected AbstractCallback() {
        Type superClass = super.getClass().getGenericSuperclass();
        this.mType = ((ParameterizedType) superClass).getActualTypeArguments()[0];
    }


    /**
     * 请求成功回调
     *
     * @param response 返回参数
     */
    public abstract void onSuccess(T response);


    /**
     * 请求出错返回错误消息
     *
     * @param msg msg
     */
    public abstract void onError(String msg);


    /**
     * 请求产生异常
     *
     * @param e Exception
     */
    public abstract void onFailure(Exception e);

}
