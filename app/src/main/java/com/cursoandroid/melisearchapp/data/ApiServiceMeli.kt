package com.cursoandroid.melisearchapp.data

import com.cursoandroid.melisearchapp.common.Constants
import com.cursoandroid.melisearchapp.data.models.ItemDetail
import com.cursoandroid.melisearchapp.data.models.InfoResponse
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/*
 * Interface que construye el objeto retrofit, y conecta a la API de Mercadolibre (URL_BASE_ML).
 */

interface ApiServiceMeli {
    companion object{
        var instance : ApiServiceMeli? = null
            get() {
                if(field == null){
                    //Retrofit constructor.
                    val retrofit = Retrofit.Builder()
                        .baseUrl(Constants.URL_BASE_ML)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()

                    //Inicio objecto Retrofit.
                    instance = retrofit.create(ApiServiceMeli::class.java)
                }
                return field
            }
    }

    @GET("sites/MLA/search")
    fun searchProduct(@Query("q") query : String) : Call<InfoResponse>

    @GET("items/{id}/descriptions")
    fun getDetailProduct(@Path("id")id:String) : Call<ArrayList<ItemDetail>>

    @GET("items/{id}")
    fun getItems(@Path("id")id:String): Call<ItemDetail>

}