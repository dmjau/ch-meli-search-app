package com.cursoandroid.melisearchapp.data

import com.cursoandroid.melisearchapp.common.Constants
import com.cursoandroid.melisearchapp.data.models.Detail
import com.cursoandroid.melisearchapp.data.models.Response
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiServiceMeli {
    companion object{
        var instance : ApiServiceMeli? = null
            get() {
                if(field == null){
                    //Retrofit constructor
                    val retrofit = Retrofit.Builder()
                        .baseUrl(Constants.MELI_URL_BASE)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()

                    //Init Retrofit object.
                    instance = retrofit.create(ApiServiceMeli::class.java)
                }
                return field
            }
    }

    @GET("sites/MLA/search")
    fun searchProduct(@Query("q") query : String) : Call<Response>

    @GET("items/{id}/descriptions")
    fun getDetailProduct(@Path("id")id:String) : Call<ArrayList<Detail>>

    @GET("items/{id}")
    fun getItems(@Path("id")id:String): Call<Detail>

}