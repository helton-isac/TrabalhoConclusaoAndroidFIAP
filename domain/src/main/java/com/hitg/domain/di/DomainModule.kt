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
            poiRepository = get()
        )
    }
    factory {
        EditPointOfInterestUseCase(
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
    factory {
        ToggleFeatureUseCase(
            toggleFeatureRepository = get()
        )
    }
}

val domainModule = listOf(useCaseModule)