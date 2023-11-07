package com.lexwilliam.timerlist

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.datetime.Clock

class MainViewModel(

): ViewModel() {

    private val _timerList = MutableStateFlow(emptyList<TimerItem>())
    val timerList = _timerList.asStateFlow()

    private val _isFilterShown = MutableStateFlow(false)
    val isFilterShown = _isFilterShown.asStateFlow()

    fun onTimerAdded() {
        Log.d("TAG", "Time Added")
        _timerList.value = _timerList.value + TimerItem()
    }

    fun updateTimer() {
        _timerList.update { old -> old.map { timer -> timer.copy(displayTime = Clock.System.now().epochSeconds - timer.startAt.epochSeconds) } }
    }

    fun popTimer() {
        _timerList.update { old -> old.filterNot { timer -> timer.id == old.firstOrNull()?.id } }
    }

    fun onChangeColumnClicked() {
        _isFilterShown.update { old -> !old }
    }

}