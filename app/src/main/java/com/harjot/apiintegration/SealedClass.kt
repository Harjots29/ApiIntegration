package com.harjot.apiintegration

//sealed is a keyword used to represent restricted class hierarchies
//it handles responses
//saare responses yaha se bind and filter ho kr aate hai

sealed class SealedClass<out T> {
    data class Success <out T>(val data:T?=null):SealedClass<T>()
    data class Loading (val nothing: Nothing?=null):SealedClass<Nothing>()
    data class Error (val msg:String?=null):SealedClass<Nothing>()
}