package com.cursoandroid.melisearchapp.repository

import com.cursoandroid.melisearchapp.data.ApiServiceMeli
import com.cursoandroid.melisearchapp.data.models.Detail
import com.cursoandroid.melisearchapp.data.models.Response
import retrofit2.Call
import retrofit2.Callback

class MeLiRepository {
    //initianlized api service
    var apiServiceMeli: ApiServiceMeli? = null
    init {
        apiServiceMeli = ApiServiceMeli.instance
    }

    //Call method Search
    fun responseSearch(
        product: String,
        onFailure: (call: Call<Response>, t: Throwable) -> Unit,
        onResponse: (call: Call<Response>, response: retrofit2.Response<Response>) -> Unit) {
        apiServiceMeli?.searchProduct(product)?.enqueue(object : Callback<Response> {
            override fun onFailure(call: Call<Response>, t: Throwable) {
                onFailure(call, t);
            }
            override fun onResponse(call: Call<Response>, response: retrofit2.Response<Response>) {
                onResponse(call, response)
            }
        })
    }

    //Call method Detail
    fun responseDetail(
        idProduct: String,
        onFailure: (call: Call<ArrayList<Detail>>, t: Throwable) -> Unit,
        onResponse: (call: Call<ArrayList<Detail>>, response: retrofit2.Response<ArrayList<Detail>>) -> Unit
    ){
        apiServiceMeli?.getDetailProduct(idProduct)?.enqueue(object: Callback<ArrayList<Detail>> {
            override fun onFailure(call: Call<ArrayList<Detail>>, t: Throwable) {
                onFailure(call,t)
            }

            override fun onResponse(call: Call<ArrayList<Detail>>, response: retrofit2.Response<ArrayList<Detail>>) {
                onResponse(call,response)
            }
        })
    }

    //Call method Get item
    fun responseGetItem(
        idItem:String,
        onFailure: (call: Call<Detail>, t: Throwable) -> Unit,
        onResponse: (call: Call<Detail>, response: retrofit2.Response<Detail>) -> Unit
    ){
        apiServiceMeli?.getItems(idItem)?.enqueue(object: Callback<Detail> {
            override fun onFailure(call: Call<Detail>, t: Throwable) {
                onFailure(call,t)
            }

            override fun onResponse(call: Call<Detail>, response: retrofit2.Response<Detail>) {
                onResponse(call,response)
            }
        })
    }
}