package com.example.esemkalibrary.services;

public interface AsyncCallBack {
    void OnComplete(int statusCode, String result);
    void OnLoading();
}
