package com.example.rentateamtest.helpers

import android.content.Context
import androidx.room.Room
import com.example.rentateamtest.db.Database
import com.example.rentateamtest.http.WebService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Modules {

    @Provides
    @Singleton
    fun provideWebService(): WebService {
        val retrofit = Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://reqres.in/api/")
            .build()

        return retrofit.create(WebService::class.java);
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context)= Room
        .databaseBuilder(context, Database::class.java,"rentateam")
        .build()

    @Provides
    @Singleton
    fun provideUserDao(db: Database) = db.userDao()
}