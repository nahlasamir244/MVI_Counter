package com.nahlasamir244.mvi_counter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    lateinit var numberTextView:TextView
    lateinit var incrementButton:Button
    private val mainViewModel:MainViewModel by lazy {
        ViewModelProviders.of(this).get(MainViewModel::class.java)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        numberTextView = findViewById(R.id.number_textview)
        incrementButton = findViewById(R.id.increment_button)
        render()
        incrementButton.setOnClickListener {
            //send
            lifecycleScope.launch {
                mainViewModel.intentChannel.send(MainIntent.Increment)
            }

        }
    }
    //send action fun (fire intent)
    private fun send(){

    }
    //render fun (view state)
    private fun render(){
        lifecycleScope.launchWhenStarted {
            mainViewModel.state.collect {
                when(it){
                    is MainViewState.Idle -> {
                        numberTextView.text = "Idle"
                    }
                    is MainViewState.Result -> {
                        numberTextView.text = it.number.toString()
                    }
                    is MainViewState.Error -> {
                        numberTextView.text = it.error
                    }
                }
            }
        }
    }
}