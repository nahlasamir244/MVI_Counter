package com.nahlasamir244.mvi_counter

sealed class MainViewState{
    //here we put all the possible view states
    //example : loading , result , error
    //here we have : idle , result , error
    object Idle : MainViewState()
    data class Result(val number:Int):MainViewState()
    data class Error(val error:String):MainViewState()
}
