package com.mstc.mstcapp.util;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.mstc.mstcapp.util.Constants.BASE_URL;

public class RetrofitInstance {
    private static Retrofit retrofit;

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
