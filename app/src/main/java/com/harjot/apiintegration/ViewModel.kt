package com.harjot.apiintegration

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.harjot.apiintegration.models.ResponseModel
import kotlinx.coroutines.launch

class VMclass: ViewModel() {
    var getRes:MutableLiveData<SealedClass<ResponseModel>?> = MutableLiveData()
    // mutableLiveData auto hits the api when updated in server
    // Live data is fetched from server when it is updated
    val uRepo = Repo()
    private  val TAG = "ViewModel"

    fun apiHit(){
        getRes.value = null
        getRes.value = SealedClass.Loading()
        viewModelScope.launch {
            //it only works when data is coming from server
            try {
                val response = uRepo.userRepo()
                if (response.code() == 200){
                    if (response.body()!=null){
                        getRes.setValue(SealedClass.Success(response.body()))
                        Log.e(TAG, "getData: success: ${response.body()}",)
                    }
                }else{
                    Log.e(TAG, "getData: failure: ${response.body()}", )
                    getRes.value = SealedClass.Error(response.message())
                }
            }catch (e:Exception){
                e.printStackTrace()
                //printStackTrace() provides a snippet of a particular code error
                //gives description of each line briefly
            }
        }
    }
}