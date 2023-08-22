package com.example.guessgame

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainActivityViewModel: ViewModel() {
    private val _twoDigit = MutableStateFlow(false)
    val twoDigitState: StateFlow<Boolean> = _twoDigit.asStateFlow()


    private var _threeDigit = MutableStateFlow(false)
    val threeDigitState: StateFlow<Boolean> = _threeDigit.asStateFlow()

    private var _fourDigit = MutableStateFlow(false)
    val fourDigitState: StateFlow<Boolean> = _fourDigit.asStateFlow()

    private var _noneCheck = MutableStateFlow(false)
    val noneCheckState: StateFlow<Boolean> = _noneCheck.asStateFlow()

    fun onTwoDigit() {
        _twoDigit.value = true
    }
    fun onThreeDigit() {
        _threeDigit.value = true
    }
    fun onFourDigit() {
        _fourDigit.value = true
    }
    fun noneChecked(){
        _noneCheck.value = true
    }
}