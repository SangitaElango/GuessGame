package com.example.guessgame

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.*
import java.util.*


class GameActivityViewModel : ViewModel() {

    private var ultGuessNum: Int = 0
    private val gameUiState = MutableStateFlow(GameUiState())
    val uiState: StateFlow<GameUiState> = gameUiState.asStateFlow()

    private val _gameOverState = MutableStateFlow(false)
    val gameOverState: StateFlow<Boolean> = _gameOverState.asStateFlow()


    private var _gameWinState = MutableStateFlow(false)
    val gameWinState: StateFlow<Boolean> = _gameWinState.asStateFlow()

    private var _noneCheck = MutableStateFlow(false)
    val noneCheckState: StateFlow<Boolean> = _noneCheck.asStateFlow()

    fun play(currGuessStr: String?){
        if(currGuessStr.isNullOrEmpty()) noneChecked()
        else{
            val currGuess: Int? = currGuessStr?.toInt()

            Log.d("currGuess", "currGuess "+currGuess.toString())
            val curState = gameUiState.value

            if(curState.numberOfChances<=0){
                onGameOver()
            }
            else{
                if(ultGuessNum==currGuess){
                    onGameWin()
                }
                else{
                    var hintStr: String? = null
                    hintStr = if(ultGuessNum< currGuess!!){
                        "Hint: Decrease your guess"
                    } else{
                        "Hint: Increase your guess"
                    }
                    gameUiState.update { currentState -> currentState.copy(
                        lastGuess = currGuess,
                        hint = hintStr,
                        numberOfChances = currentState.numberOfChances - 1,
                    ) }
                }

            }
        }

    }
    private fun onGameOver() {
        _gameOverState.update { true }
        Log.d("_gameOverState", "_gameOverState "+_gameOverState.value.toString())
    }
    private fun onGameWin() {
        _gameWinState.value = true
    }
    private fun noneChecked(){
        _noneCheck.value = true
    }
    fun setInput(numRange: String) {
        val r = Random()
        when (numRange) {
            ("two") -> ultGuessNum = r.nextInt(90) + 10
            ("three") -> ultGuessNum = r.nextInt(900) + 100
            ("four") -> ultGuessNum = r.nextInt(9000) + 1000
        }
        Log.d("ultGuessNum", "ultGuessNum "+ultGuessNum.toString())

    }
}