package com.fiap.meurole.di

import com.fiap.meurole.about.AboutViewModel
import com.fiap.meurole.base.BaseViewModel
import com.fiap.meurole.base.auth.BaseAuthViewModel
import com.fiap.meurole.home.HomeViewModel
import com.fiap.meurole.login.LoginViewModel
import com.fiap.meurole.map.MapViewModel
import com.fiap.meurole.pointOfInterest.create.CreatePointOfInterestViewModel
import com.fiap.meurole.pointOfInterest.edit.EditPointOfInterestViewModel
import com.fiap.meurole.profile.ProfileViewModel
import com.fiap.meurole.roadmap.CreateRoadmapViewModel
import com.fiap.meurole.roadmapList.RoadmapListViewModel
import com.fiap.meurole.signup.SignUpViewModel
import com.fiap.meurole.splash.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    viewModel { SplashViewModel() }
    viewModel { BaseViewModel() }
    viewModel { MapViewModel() }
    viewModel {
        BaseAuthViewModel(
            getUserLoggedUseCase = get()
        )
    }
    viewModel {
        HomeViewModel(
            getUserLoggedUseCase = get(),
            fetchRoadmapsUseCase = get()
        )
    }
    viewModel {
        LoginViewModel(
            loginUseCase = get(),
            biometricsUseCase = get(),
            toggleFeatureUseCase = get(),
            logAnalyticsEvent = get()
        )
    }
    viewModel {
        ProfileViewModel(
            getUserLoggedUseCase = get(),
            loginUseCase = get()
        )
    }
    viewModel {
        SignUpViewModel(
            createUserUseCase = get()
        )
    }
    viewModel {
        CreateRoadmapViewModel(
            createRoadmapUseCase = get(),
            deletePointOfInterestUseCase = get()
        )
    }
    viewModel {
        CreatePointOfInterestViewModel(
            createPointOfInterestUseCase = get()
        )
    }
    viewModel {
        EditPointOfInterestViewModel(
            editPointOfInterestUseCase = get()
        )
    }
    viewModel {
        RoadmapListViewModel(
            fetchRoadmapsUseCase = get()
        )
    }
    viewModel {
        AboutViewModel(
            getSloganUseCase = get(),
            logAnalyticsEvent = get()
        )
    }
}