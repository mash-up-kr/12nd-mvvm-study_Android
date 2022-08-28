package com.example.week1.module

import com.example.week1.domain.repository.SearchRepository
import com.example.week1.domain.usecase.GetSearchListUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {

    @Provides
    fun provideGetSearchListUseCase(searchRepository: SearchRepository): GetSearchListUseCase =
        GetSearchListUseCase(searchRepository)
}