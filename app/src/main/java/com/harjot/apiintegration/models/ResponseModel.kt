package com.harjot.apiintegration.models

//alt + k shortcut for opening the json to kotlin class converter
data class ResponseModel(
    val `data`: List<Data>,
    val page: Int?=0,
    val per_page: Int?=0,
    val support: Support?= Support(),
    val total: Int?=0,
    val total_pages: Int?=0
){

    data class Data(
        val avatar: String?="",
        val email: String?="",
        val first_name: String?="",
        val id: Int?=0,
        val last_name: String?=""
    )

    data class Support(
        val text: String?="",
        val url: String?=""
    )
}