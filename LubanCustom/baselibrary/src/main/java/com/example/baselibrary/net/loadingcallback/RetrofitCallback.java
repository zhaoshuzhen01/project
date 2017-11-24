package com.example.baselibrary.net.loadingcallback;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public  class RetrofitCallback<T> implements Callback<T> {

    @Override
    public void onResponse(Call<T> call, Response<T> response) {

        if(response.isSuccessful()) {

        } else {
            onFailure(call, new Throwable(response.message()));
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {

    }

//    public abstract void onSuccess(Call<T> call, Response<T> response);

    public void onLoading(long total, long progress) {

    }
}