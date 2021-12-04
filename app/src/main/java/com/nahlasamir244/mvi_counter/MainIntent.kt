package com.nahlasamir244.mvi_counter

sealed class MainIntent{
    //if it takes param use data class otherwise use object
    object Increment:MainIntent()
}
