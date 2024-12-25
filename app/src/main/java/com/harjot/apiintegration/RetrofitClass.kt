package com.harjot.apiintegration

import com.harjot.apiintegration.Constants.Companion.Base_Url
import com.harjot.apiintegration.interfaces.ApiInterface
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClass {
    companion object{
        private val retrofit by lazy {
            //Lazy means we only initialize

            val logging = HttpLoggingInterceptor()
            //Logging interceptor used for showing the response in log or console

            //attaching to retrofit object
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
            //network client
            val client = OkHttpClient.Builder().addInterceptor(logging).build()

            //pass the client to retrofit instance
            Retrofit.Builder()
                .baseUrl(Base_Url)
                .addConverterFactory(GsonConverterFactory.create())
                //addConverterFactory is used to determine how the response should be interpreted and converted to kotlin object
                .client(client)
                .build()
        }

        //get api instance from retrofit builder
        //api object
        //this can be used from everywhere to make network requests
        val api by lazy {
            retrofit.create(ApiInterface::class.java)
        }
    }
}