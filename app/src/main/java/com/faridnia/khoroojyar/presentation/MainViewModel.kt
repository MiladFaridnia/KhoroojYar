package com.faridnia.khoroojyar.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.faridnia.khoroojyar.domain.repository.DataStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
): ViewModel() {

    private val _isDark = MutableStateFlow<Boolean?>(null)
    val isDark = _isDark.asStateFlow()

    init {
        viewModelScope.launch {
            dataStoreRepository.getTimeSettings().collect { setting ->
                _isDark.update { setting.isDark }
            }
        }
    }
}