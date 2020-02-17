package com.home.mvvmclean.di

import com.home.mvvmclean.ui.MainViewModel
import dagger.Component

@Component
interface MainComponent {
    fun injectDependencies(viewModel: MainViewModel);
}