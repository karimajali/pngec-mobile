package com.pngelectoral.ui.retrofit

import com.pngelectoral.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiClient {
    private var mRetrofitInterface: RetrofitInterface? = null
    private var mLocationsRetrofitInterface: RetrofitInterface? = null
    private var mVotersRetrofitInterface: RetrofitInterface? = null
    private val BASE_URL = BuildConfig.BASE_URL

    fun getRetrofitInterface(): RetrofitInterface? {
        if (mRetrofitInterface == null) {
            val logging = HttpLoggingInterceptor()
            logging.level = (HttpLoggingInterceptor.Level.BODY)
            val httpClient = OkHttpClient.Builder()
            httpClient.addInterceptor(logging)

            // httpClient.addInterceptor(LoggerInterceptor())
            httpClient.callTimeout(10, TimeUnit.MINUTES)
            httpClient.readTimeout(5, TimeUnit.MINUTES)
            httpClient.writeTimeout(10, TimeUnit.MINUTES)
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build()
            mRetrofitInterface = retrofit.create(RetrofitInterface::class.java)
        }

        return mRetrofitInterface


    }

    fun getLocationsRetrofitInterface(): RetrofitInterface? {

        if (mLocationsRetrofitInterface == null) {

            val logging = HttpLoggingInterceptor()
            logging.level = (HttpLoggingInterceptor.Level.BODY)
            val httpClient = OkHttpClient.Builder()
            httpClient.addInterceptor(logging)

            // httpClient.addInterceptor(LoggerInterceptor())
            httpClient.callTimeout(10, TimeUnit.MINUTES)
            httpClient.readTimeout(5, TimeUnit.MINUTES)
            httpClient.writeTimeout(10, TimeUnit.MINUTES)
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build()
            mLocationsRetrofitInterface = retrofit.create(RetrofitInterface::class.java)
        }

        return mLocationsRetrofitInterface


    }

    fun getVotersRetrofitInterface(): RetrofitInterface? {

        if (mVotersRetrofitInterface == null) {

            val logging = HttpLoggingInterceptor()
            logging.level = (HttpLoggingInterceptor.Level.BODY)
            val httpClient = OkHttpClient.Builder()
            httpClient.addInterceptor(logging)

            // httpClient.addInterceptor(LoggerInterceptor())
            httpClient.callTimeout(10, TimeUnit.MINUTES)
            httpClient.readTimeout(5, TimeUnit.MINUTES)
            httpClient.writeTimeout(10, TimeUnit.MINUTES)
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build()
            mVotersRetrofitInterface = retrofit.create(RetrofitInterface::class.java)
        }

        return mVotersRetrofitInterface
    }


}
