package com.arjun.food2fork.di.network

import com.arjun.food2fork.RestApi
import com.arjun.food2fork.di.application.ApplicationScope
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
class NetworkModule {

    @ApplicationScope
    @Provides
    fun providesOkhttpClient(): OkHttpClient {
        return OkHttpClient().newBuilder()
            .build()
    }

    @ApplicationScope
    @Provides
    fun provideRetrofitBuilder(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    @ApplicationScope
    @Provides
    fun provideRestApi(retrofit: Retrofit): RestApi {
        return retrofit.create(RestApi::class.java)
    }
}