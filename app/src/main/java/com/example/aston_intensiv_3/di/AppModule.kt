package com.example.aston_intensiv_3.di

import com.example.aston_intensiv_3.data.ContactsRepoImpl
import com.example.aston_intensiv_3.domain.ContactsRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideRepo(): ContactsRepo {
        return ContactsRepoImpl()
    }
}