package com.faridnia.khoroojyar.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.faridnia.khoroojyar.domain.repository.DataStoreRepository
import com.faridnia.khoroojyar.util.toLocalTime
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.Duration
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {

    private val _state = MutableStateFlow(SettingState())
    val state = _state.asStateFlow()

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()


    private var selectedSettingType: SettingType? = null

    init {
        viewModelScope.launch {
            dataStoreRepository.getTimeSettings().collect { timeSettings ->
                _state.update {
                    it.copy(
                        earliestStart = timeSettings.earliestStart,
                        latestStart = timeSettings.latestStart,
                        workDuration = timeSettings.workDuration,
                        isDark = timeSettings.isDark,
                        areNotificationsEnabled = timeSettings.areNotificationsEnabled
                    )
                }
            }
        }
    }

    fun onEvent(event: SettingsEvent) {
        when (event) {
            SettingsEvent.EarliestStartClicked -> {
                selectedSettingType = SettingType.EARLIEST_START
                _state.update {
                    it.copy(
                        showTimePickerDialog = true,
                        currentTimeForTimePicker = state.value.earliestStart
                    )
                }
            }

            SettingsEvent.LatestStartClicked -> {
                selectedSettingType = SettingType.LATEST_START
                _state.update {
                    it.copy(
                        showTimePickerDialog = true,
                        currentTimeForTimePicker = state.value.latestStart
                    )
                }
            }

            SettingsEvent.WorkDurationClicked -> {
                selectedSettingType = SettingType.WORK_DURATION
                _state.update {
                    it.copy(
                        showTimePickerDialog = true,
                        currentTimeForTimePicker = state.value.workDuration?.toLocalTime()
                    )
                }
            }

            SettingsEvent.TimeDialogDismissed -> {
                _state.update { it.copy(showTimePickerDialog = false) }
            }

            is SettingsEvent.TimeConfirmed -> {
                handleTimeConfirmed(event.hour, event.minute)
            }

            is SettingsEvent.DarkModeClicked -> {
                viewModelScope.launch {
                    dataStoreRepository.updateDarkMode(event.isDark)
                }.invokeOnCompletion {
                    _state.update { it.copy(isDark = event.isDark) }
                }
            }
            is SettingsEvent.NotificationsToggled -> {
                viewModelScope.launch {
                    dataStoreRepository.updateNotificationsEnabled(event.areEnabled)
                }.invokeOnCompletion {
                    _state.update { it.copy(areNotificationsEnabled = event.areEnabled) }
                }
            }

            SettingsEvent.ProfileClicked -> {
                viewModelScope.launch {
                    _eventFlow.emit(UiEvent.NavigateToProfile)
                }
            }
        }
    }

    private fun handleTimeConfirmed(hour: Int, minute: Int) {
        when (selectedSettingType) {
            SettingType.EARLIEST_START -> {
                val earliestStart = LocalTime.of(hour, minute)
                viewModelScope.launch {
                    dataStoreRepository.updateEarliestStart(earliestStart)
                }.invokeOnCompletion {
                    _state.update { it.copy(earliestStart = earliestStart) }
                }
            }

            SettingType.LATEST_START -> {
                val latestStart = LocalTime.of(hour, minute)
                viewModelScope.launch {
                    dataStoreRepository.updateLatestStart(latestStart)
                }.invokeOnCompletion {
                    _state.update { it.copy(latestStart = latestStart) }
                }
            }

            SettingType.WORK_DURATION -> {
                val workDuration =
                    Duration.ofHours(hour.toLong()).plusMinutes(minute.toLong())
                viewModelScope.launch {
                    dataStoreRepository.updateWorkDuration(workDuration)
                }.invokeOnCompletion {
                    _state.update { it.copy(workDuration = workDuration) }
                }
            }

            null -> {}
        }
    }

    sealed class UiEvent {
        data object NavigateToProfile : UiEvent()
    }
}