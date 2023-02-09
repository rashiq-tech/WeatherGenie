package com.rashiq_tech.weathergenie.di

import android.content.Context
import androidx.core.app.NotificationCompat
import com.rashiq_tech.weathergenie.R
import com.rashiq_tech.weathergenie.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
class MainModule {

    @ActivityScoped
    @Provides
    fun provideNotificationCompat(@ApplicationContext applicationContext: Context) = NotificationCompat.Builder(applicationContext, Constants.CHANNEL_ID)
        .setContentTitle(applicationContext.getString(R.string.app_name))
        .setSmallIcon(R.drawable.ic_close_white)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
}