package com.ezzy.data.di

import android.content.Context
import com.ezzy.data.data.GoogleAuthUiClient
import com.ezzy.data.data.ListingRepositoryImpl
import com.ezzy.data.data.PreferenceRepositoryImpl
import com.ezzy.data.domain.repository.ListingsRepository
import com.ezzy.data.domain.repository.PreferenceRepository
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.lang.reflect.Modifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideListingRepository(@ApplicationContext context: Context): ListingsRepository =
        ListingRepositoryImpl(context)

    @Provides
    @Singleton
    fun providePreferencesRepository(@ApplicationContext context: Context,
                                     gson: Gson): PreferenceRepository =
        PreferenceRepositoryImpl(context, gson)


    @Provides
    @Singleton
    fun provideOneTapClient(@ApplicationContext context: Context): SignInClient =
        Identity.getSignInClient(context)

    @Provides
    @Singleton
    fun provideGoogleAuthUiClient(
        @ApplicationContext context: Context,
        onTapClient: SignInClient
    ): GoogleAuthUiClient =
        GoogleAuthUiClient(context, onTapClient)

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .excludeFieldsWithModifiers(Modifier.TRANSIENT) // STATIC|TRANSIENT in the default configuration
            .create()
    }

}