package com.task.notes.module

import com.task.notes.constants.Constants.BASE_URL
import com.task.notes.data.remote.RetrofitApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(ViewModelComponent::class)
object NetworkModule {

    @ViewModelScoped
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @ViewModelScoped
    @Provides
    fun provideRetrofitApi(
        retrofit: Retrofit
    ): RetrofitApi {
        return retrofit.create(RetrofitApi::class.java)
    }

}