package com.example.guessgame

import android.content.Intent
import android.os.Bundle
import android.os.Process
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import com.example.guessgame.databinding.ActivityGameBinding
import kotlinx.coroutines.launch


class GameActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGameBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val receivedData = intent.getStringExtra("Key").toString()

        val viewModel: GameActivityViewModel by viewModels()
        if (receivedData != null) {
            Log.d("receivedData", "receivedData "+receivedData.toString())
            viewModel.setInput(receivedData)
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { gameUiState ->
                    binding.textViewchance.text =
                        "Chance Remaining : " + gameUiState.numberOfChances.toString()
                    if (gameUiState.hint != null)
                        binding.textViewhint.text = gameUiState.hint
                    if (gameUiState.lastGuess != null)
                        binding.textViewguess.text =
                            "Your Last Guess : " + gameUiState.lastGuess.toString()
                }
            }}
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.gameWinState.collect { isGameWin ->
                    if (isGameWin) {
                        showGameWinDialog()
                    }

                }
            }}
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.gameOverState.collect{ isGameOver ->
                    if (isGameOver) {

                        showGameOverDialog()
                    }
                }

            }
        }
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.noneCheckState.collect { isNoneCheck ->
                    if (isNoneCheck) {
                        showToast()
                    }

                }
            }}
        binding.buttonConfirm.setOnClickListener{
            val guessNumber: String = binding.editTextAnswer.text.toString()
            Log.d("guessNumber", "guessNumber "+guessNumber.toString())
            viewModel.play(guessNumber)
        }
    }

    private fun showGameWinDialog() {
        val alertDialog = AlertDialog.Builder(this@GameActivity)
        alertDialog.setTitle("HURRAY")
            .setMessage("Your guess is correct. Do you want to play again?").setNegativeButton(
                "No"
            ) { dialogInterface, i ->
                moveTaskToBack(true)
                Process.killProcess(Process.myPid())
                System.exit(1)
            }.setPositiveButton(
                "YES"
            ) { dialogInterface, i ->
                val intent = Intent(this@GameActivity, MainActivity::class.java)
                startActivity(intent)
            }.show()
    }

    private fun showGameOverDialog() {
        val alertDialog = AlertDialog.Builder(this@GameActivity)
        alertDialog.setTitle("Oops!!")
            .setMessage("Your Chance are Over. Do you want to play again?").setNegativeButton(
                "No"
            ) { dialogInterface, i ->
                moveTaskToBack(true)
                Process.killProcess(Process.myPid())
                System.exit(1)
            }.setPositiveButton(
                "YES"
            ) { dialogInterface, i ->
                val intent = Intent(this@GameActivity, MainActivity::class.java)
                startActivity(intent)
            }.show()
    }
    private fun showToast(){
        Toast.makeText(this@GameActivity, "Please enter a number", Toast.LENGTH_LONG).show()
    }
}