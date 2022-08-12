package com.merryblue.myvoicealarm.common.di.module


import com.merryblue.myvoicealarm.data.repo.UserAlarmRepoImpl
import com.merryblue.myvoicealarm.domain.repo.UserAlarmRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepoModule() {

    @Provides
    @Singleton
    fun provideUserAlarmRepo(repoImpl: UserAlarmRepoImpl): UserAlarmRepo {
        return repoImpl
    }
}