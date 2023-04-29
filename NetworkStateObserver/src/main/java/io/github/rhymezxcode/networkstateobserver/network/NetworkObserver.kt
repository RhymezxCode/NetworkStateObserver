package io.github.rhymezxcode.networkstateobserver.network

import kotlinx.coroutines.flow.Flow

interface NetworkObserver {
    fun observe(): Flow<Status>

    enum class Status {
        Available, Unavailable, Losing, Lost
    }
}