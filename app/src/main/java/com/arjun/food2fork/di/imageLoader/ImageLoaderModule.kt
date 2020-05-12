package com.arjun.food2fork.di.imageLoader

import android.app.Application
import coil.ImageLoader
import coil.util.CoilUtils
import com.arjun.food2fork.di.application.ApplicationScope
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient

@Module
class ImageLoaderModule {

    @ApplicationScope
    @Provides
    fun provideImageLoader(
        application: Application,
        okHttpClientBuilder: OkHttpClient.Builder
    ): ImageLoader {
        return ImageLoader.Builder(application)
            .okHttpClient {
                okHttpClientBuilder
                    .cache(CoilUtils.createDefaultCache(application))
                    .build()
            }
            .availableMemoryPercentage(0.25)
            .crossfade(true)
            .build()
    }

}