package com.example.tasks.service.repository.remote;

import com.example.tasks.service.constants.TaskConstants;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static String BASE_URL = "http://devmasterteam.com/CursoAndroidAPI/";
    private static Retrofit retrofit;
    private static String tokenKey = "";
    private static String personKey = "";

    private RetrofitClient() {

    }

    public static Retrofit getRetrofitInstance() {

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request()
                        .newBuilder()
                        .addHeader(TaskConstants.SHARED.TOKEN_KEY, tokenKey)
                        .addHeader(TaskConstants.SHARED.PERSON_KEY, personKey)
                        .build();
                return chain.proceed(request);
            }
        });

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(httpClient.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static <S> S createService(Class<S> sClass) {
        return getRetrofitInstance().create(sClass);
    }

    public static void saveHeaders(String token, String person) {
        tokenKey = token;
        personKey = person;
    }
}
