package com.harjot.apiintegration.interfaces

interface RecyclerInterface {
    fun listClick(position:Int)
    fun onEditClick(position: Int)
    fun onDeleteClick(position: Int)
}