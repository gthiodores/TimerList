package com.lexwilliam.timerlist

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import java.util.UUID

data class TimerItem(
    val id: UUID = UUID.randomUUID(),
    val startAt: Instant = Clock.System.now(),
    val displayTime: Long = 0,
    val priority: Priority = Priority.NEUTRAL
)

enum class Priority(val value: Int) {
    HIGH(2), NEUTRAL(1), LOW(0)
}