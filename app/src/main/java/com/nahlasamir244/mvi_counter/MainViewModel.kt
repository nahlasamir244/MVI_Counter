package com.nahlasamir244.mvi_counter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

class MainViewModel:ViewModel() {
    //to send intent from activity to view model
    val intentChannel=Channel<MainIntent>(Channel.UNLIMITED)
    //to send the result view state to main activity in reduce
    private val _viewState =
        MutableStateFlow<MainViewState>(MainViewState.Idle)
    val state:StateFlow<MainViewState> get() = _viewState
    private var number:Int =0
    init {
        processIntent()
    }
    //process Intent to produce result
    private fun processIntent(){
        viewModelScope.launch {
            intentChannel.consumeAsFlow().collect {
                //check to match all the list of intents to choose the proper
                //reduce result function accordingly
                when(it){
                    is MainIntent.Increment -> {
                        reduceResult_IncrementNumber()
                    }
                }
            }
        }

    }
    //reduce the result to view state
    private fun reduceResult_IncrementNumber(){
        viewModelScope.launch {
            _viewState.value =
                try {
                    MainViewState.Result(++number)
                }
                catch (exception:Exception){
                    MainViewState.Error(exception.localizedMessage)
                }
        }
    }
}