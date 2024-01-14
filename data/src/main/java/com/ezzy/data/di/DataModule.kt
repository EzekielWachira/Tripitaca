package com.ezzy.data.di

import android.content.Context
import com.ezzy.data.data.ListingRepositoryImpl
import com.ezzy.data.domain.repository.ListingsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideListingRepository(@ApplicationContext context: Context): ListingsRepository =
        ListingRepositoryImpl(context)

}