package com.example.guessgame

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.guessgame.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val viewModel: MainActivityViewModel by viewModels()
        binding.buttonmain.setOnClickListener{
            val intent = Intent(this, GameActivity::class.java)
            if (!binding.radioButtontwo.isChecked && !binding.radioButtonthree.isChecked && !binding.radioButtonfour.isChecked)
                viewModel.noneChecked()
            else {
                if (binding.radioButtontwo.isChecked)
                    viewModel.onTwoDigit()
                else if (binding.radioButtonthree.isChecked)
                    viewModel.onThreeDigit()
                else if (binding.radioButtonfour.isChecked)
                    viewModel.onFourDigit()

            }

            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.twoDigitState.collect { isTwoDigit ->
                        if (isTwoDigit) {
                            startIntent("two")
                        }

                    }
                }}
            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.threeDigitState.collect { isThreeDigit ->
                        if (isThreeDigit) {
                            startIntent("three")
                        }

                    }
                }}
            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.fourDigitState.collect { isFourDigit ->
                        if (isFourDigit) {
                            startIntent("four")
                        }

                    }
                }}

            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.noneCheckState.collect { isNoneCheck ->
                        if (isNoneCheck) {
                            showSnackBar()
                        }

                    }
                }}

        }
    }

    private fun startIntent(value: String) {
        val intent = Intent(this, GameActivity::class.java)
        intent.putExtra("Key", value)
        startActivity(intent)
    }
    private fun showSnackBar(){
        Snackbar.make(
            binding.root,
            "Please choose an option",
            Snackbar.LENGTH_LONG
        ).show()
    }
}