package com.samkt.bibleapp.di

import com.samkt.bibleapp.feature_bible.data.BibleApi
import com.samkt.bibleapp.feature_bible.data.repository.BibleRepositoryImpl
import com.samkt.bibleapp.feature_bible.domain.repository.BibleRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private fun okHttpClientFactory(): OkHttpClient {
        return OkHttpClient().newBuilder()
            .addInterceptor { chain ->
                chain.request().newBuilder()
                    .addHeader("api-key", "e84c248f4da0154438aac812b2ea0688")
                    .build()
                    .let(chain::proceed)
            }
            .build()
    }

    @Provides
    @Singleton
    fun provideBibleApi(): BibleApi {
        return Retrofit
            .Builder()
            .baseUrl(BibleApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClientFactory())
            .build()
            .create(BibleApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRepository(api: BibleApi):BibleRepository{
        return BibleRepositoryImpl(api)
    }

}