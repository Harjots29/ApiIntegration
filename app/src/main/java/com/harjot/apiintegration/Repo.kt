package com.harjot.apiintegration

import com.harjot.apiintegration.models.ResponseModel

class Repo {
    //it provides threads to each response
    //it does not block any response if another response occur at same time
    suspend fun userRepo():retrofit2.Response<ResponseModel>{
        return RetrofitClass.api.getData()
    }
}