package com.example.webrtc.utils

import android.app.Application
import android.content.Context
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

    @Provides
    fun providesApplication(application: Application) : Application {
        return application
    }

    @Provides
    fun providesContext(@ApplicationContext context: Context) : Context = context.applicationContext

    @Provides
    fun providesGson() : Gson = Gson()

    @Provides
    fun provideDataBaseInstance() : FirebaseDatabase = FirebaseDatabase.getInstance()

    @Provides
    fun provideDataBaseReference(db : FirebaseDatabase) : DatabaseReference = db.reference
}