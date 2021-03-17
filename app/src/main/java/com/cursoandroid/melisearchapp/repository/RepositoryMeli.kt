package com.cursoandroid.melisearchapp.repository

import com.cursoandroid.melisearchapp.data.ApiServiceMeli
import com.cursoandroid.melisearchapp.data.models.ItemDetail
import com.cursoandroid.melisearchapp.data.models.InfoResponse
import retrofit2.Call
import retrofit2.Callback
/*
 * Inicializamos el repositorio llamando a la Api con los distintos metodos.
 * Search: busqueda por termino de producto.
 * Detail: busqueda de detalles de acuerdo a un id determinado de producto.
 * GetItem: busqueda de un item particular para el detalle.
 */
class RepositoryMeli {

    var apiServiceMeli: ApiServiceMeli? = null

    init {
        apiServiceMeli = ApiServiceMeli.instance
    }

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

    fun responseDetail(
            idProduct: String,
            onFailure: (call: Call<ArrayList<ItemDetail>>, t: Throwable) -> Unit,
            onResponse: (call: Call<ArrayList<ItemDetail>>, response: retrofit2.Response<ArrayList<ItemDetail>>) -> Unit){
            apiServiceMeli?.getDetailProduct(idProduct)?.enqueue(object: Callback<ArrayList<ItemDetail>> {
                override fun onFailure(call: Call<ArrayList<ItemDetail>>, t: Throwable) {
                    onFailure(call,t)
                }
                override fun onResponse(call: Call<ArrayList<ItemDetail>>, response: retrofit2.Response<ArrayList<ItemDetail>>) {
                    onResponse(call,response)
                }
            })
    }

    fun responseGetItem(
            idItem:String,
            onFailure: (call: Call<ItemDetail>, t: Throwable) -> Unit,
            onResponse: (call: Call<ItemDetail>, response: retrofit2.Response<ItemDetail>) -> Unit){
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