package com.cursoandroid.melisearchapp.repository

import com.cursoandroid.melisearchapp.data.ApiServiceMeli
import com.cursoandroid.melisearchapp.data.models.ItemDetail
import com.cursoandroid.melisearchapp.data.models.InfoResponse
import retrofit2.Call
import retrofit2.Callback

class RepositoryMeli {
    //initianlized api service
    var apiServiceMeli: ApiServiceMeli? = null
    init {
        apiServiceMeli = ApiServiceMeli.instance
    }

    //Call method Search
    fun responseSearch(
            product: String,
            onFailure: (call: Call<InfoResponse>, t: Throwable) -> Unit,
            onResponse: (call: Call<InfoResponse>, response: retrofit2.Response<InfoResponse>) -> Unit) {
        apiServiceMeli?.searchProduct(product)?.enqueue(object : Callback<InfoResponse> {
            override fun onFailure(call: Call<InfoResponse>, t: Throwable) {
                onFailure(call, t);
            }
            override fun onResponse(call: Call<InfoResponse>, response: retrofit2.Response<InfoResponse>) {
                onResponse(call, response)
            }
        })
    }

    //Call method Detail
    fun responseDetail(
            idProduct: String,
            onFailure: (call: Call<ArrayList<ItemDetail>>, t: Throwable) -> Unit,
            onResponse: (call: Call<ArrayList<ItemDetail>>, response: retrofit2.Response<ArrayList<ItemDetail>>) -> Unit
    ){
        apiServiceMeli?.getDetailProduct(idProduct)?.enqueue(object: Callback<ArrayList<ItemDetail>> {
            override fun onFailure(call: Call<ArrayList<ItemDetail>>, t: Throwable) {
                onFailure(call,t)
            }

            override fun onResponse(call: Call<ArrayList<ItemDetail>>, response: retrofit2.Response<ArrayList<ItemDetail>>) {
                onResponse(call,response)
            }
        })
    }

    //Call method Get item
    fun responseGetItem(
            idItem:String,
            onFailure: (call: Call<ItemDetail>, t: Throwable) -> Unit,
            onResponse: (call: Call<ItemDetail>, response: retrofit2.Response<ItemDetail>) -> Unit
    ){
        apiServiceMeli?.getItems(idItem)?.enqueue(object: Callback<ItemDetail> {
            override fun onFailure(call: Call<ItemDetail>, t: Throwable) {
                onFailure(call,t)
            }

            override fun onResponse(call: Call<ItemDetail>, response: retrofit2.Response<ItemDetail>) {
                onResponse(call,response)
            }
        })
    }
}