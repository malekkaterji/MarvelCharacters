package com.malekkaterji.marvelcharacters.di

import com.malekkaterji.marvelcharacters.BuildConfig
import com.malekkaterji.marvelcharacters.BuildConfig.BASE_URL
import com.malekkaterji.marvelcharacters.data.network.MarvelApiService
import com.malekkaterji.marvelcharacters.util.Md5Utils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideHttpClient() : OkHttpClient {
        val timeStamp = Date().time.toString()

        return OkHttpClient.Builder()
            .readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .addInterceptor{
                var request = it.request()
                val url: HttpUrl = request.url().newBuilder()
                    .addQueryParameter("ts", timeStamp)
                    .addQueryParameter("apikey", BuildConfig.PUBLIC_KEY)
                    .addQueryParameter("hash", Md5Utils().generateHash("$timeStamp${BuildConfig.PRIVATE_KEY}${BuildConfig.PUBLIC_KEY}"))
                    .build()
                request = request.newBuilder().url(url).build()
                it.proceed(request)
            }
            .build()
    }

    @Singleton
    @Provides
    fun provideConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Singleton
    @Provides
    fun provideRetrofitInstance(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): MarvelApiService {
        return retrofit.create(MarvelApiService::class.java)
    }

}