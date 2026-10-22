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

    private fun buildOkHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
        
        // Only log HTTP payloads in DEBUG builds to avoid leaking citizen PII and auth tokens in production Logcat (SEC-05)
        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
            builder.addInterceptor(logging)
        } else {
            val minimalLogging = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BASIC
            }
            builder.addInterceptor(minimalLogging)
        }

        builder.callTimeout(90, TimeUnit.SECONDS)
        builder.connectTimeout(30, TimeUnit.SECONDS)
        builder.readTimeout(60, TimeUnit.SECONDS)
        builder.writeTimeout(60, TimeUnit.SECONDS)
        return builder.build()
    }

    private fun buildRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(buildOkHttpClient())
            .build()
    }

    fun getRetrofitInterface(): RetrofitInterface? {
        if (mRetrofitInterface == null) {
            mRetrofitInterface = buildRetrofit().create(RetrofitInterface::class.java)
        }
        return mRetrofitInterface
    }

    fun getLocationsRetrofitInterface(): RetrofitInterface? {
        if (mLocationsRetrofitInterface == null) {
            mLocationsRetrofitInterface = buildRetrofit().create(RetrofitInterface::class.java)
        }
        return mLocationsRetrofitInterface
    }

    fun getVotersRetrofitInterface(): RetrofitInterface? {
        if (mVotersRetrofitInterface == null) {
            mVotersRetrofitInterface = buildRetrofit().create(RetrofitInterface::class.java)
        }
        return mVotersRetrofitInterface
    }


}
