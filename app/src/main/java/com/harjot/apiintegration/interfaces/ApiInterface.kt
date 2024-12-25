package com.harjot.apiintegration.interfaces

import com.harjot.apiintegration.models.ResponseModel
import retrofit2.http.GET

interface ApiInterface {
    @GET("/api/users")
    suspend fun getData():retrofit2.Response<ResponseModel>
}