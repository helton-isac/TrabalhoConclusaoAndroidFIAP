package com.hitg.domain.di

import com.hitg.domain.usecases.CreateUserUseCase
import com.hitg.domain.usecases.GetUserLoggedUseCase
import com.hitg.domain.usecases.LoginUseCase
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
}

val domainModule = listOf(useCaseModule)