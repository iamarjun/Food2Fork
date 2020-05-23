package com.arjun.food2fork.di.network

import com.arjun.food2fork.RestApi
import com.arjun.food2fork.di.application.ApplicationScope
import com.arjun.food2fork.util.Constants
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
class NetworkModule {

    @ApplicationScope
    @Provides
    fun providesOkhttpClientBuilder(): OkHttpClient.Builder {
        return OkHttpClient().newBuilder()

    }

    @ApplicationScope
    @Provides
    fun provideRetrofitBuilder(okHttpClientBuilder: OkHttpClient.Builder): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClientBuilder.build())
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    @ApplicationScope
    @Provides
    fun provideRestApi(retrofit: Retrofit): RestApi {
        return retrofit.create(RestApi::class.java)
    }
}