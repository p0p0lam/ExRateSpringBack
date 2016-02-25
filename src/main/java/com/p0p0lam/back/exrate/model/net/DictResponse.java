package com.p0p0lam.back.exrate.model.net;

/**
 * Created by Sergey on 25.02.2016.
 */
public class DictResponse<T> {
    T data;

    public DictResponse(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
