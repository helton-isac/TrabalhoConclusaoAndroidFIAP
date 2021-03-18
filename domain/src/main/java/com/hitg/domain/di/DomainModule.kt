package com.hitg.domain.di

import com.hitg.domain.usecases.*
import org.koin.dsl.module

val useCaseModule = module {
    factory {
        GetUserLoggedUseCase(
            userRepository = get()
        )
    }
    factory {
        LoginUseCase(
            userRepository = get()
        )
    }
    factory {
        CreateUserUseCase(
            userRepository = get()
        )
    }
    factory {
        BiometricsUseCase(
            biometricsRepository = get()
        )
    }
    factory {
        CreateRoadmapUseCase(
            getUserLoggedUseCase = get(),
            roadmapRepository = get()
        )
    }
    factory {
        CreatePointOfInterestUseCase(
            getUserLoggedUseCase = get(),
            poiRepository = get()
        )
    }
    factory {
        DeletePointOfInterestUseCase(
            poiRepository = get()
        )
    }
    factory {
        FetchRoadmapsUseCase(
            roadmapRepository = get(),
            poiRepository = get()
        )
    }
    factory {
        GetSloganUseCase(
            sloganRepository = get()
        )
    }
}

val domainModule = listOf(useCaseModule)