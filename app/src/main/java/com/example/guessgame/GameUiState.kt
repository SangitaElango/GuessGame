package com.example.guessgame

data class GameUiState(
    val lastGuess: Int? = null,
    val hint: String? = null,
    val numberOfChances: Int = 10
)