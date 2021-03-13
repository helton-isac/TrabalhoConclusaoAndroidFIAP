package com.hitg.domain.entity

enum class BiometricsInstanceState(val value: Int) {
    IN_USE(0),
    NOT_IN_USE(1),
    DENIED(2),
    UNAVAILABLE(3),
}