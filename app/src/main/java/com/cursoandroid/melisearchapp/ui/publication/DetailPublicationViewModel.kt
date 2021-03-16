package com.cursoandroid.melisearchapp.ui.publication

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cursoandroid.melisearchapp.R
import com.cursoandroid.melisearchapp.common.ErrorStatusCode
import com.cursoandroid.melisearchapp.repository.RepositoryMeli
import com.cursoandroid.melisearchapp.data.models.ItemDetail

class DetailPublicationViewModel : ViewModel() {
    private val repositoryMeli: RepositoryMeli = RepositoryMeli()

    var itemDetailProduct: MutableLiveData<ArrayList<ItemDetail>> = MutableLiveData<ArrayList<ItemDetail>>()
    var item: MutableLiveData<ItemDetail> = MutableLiveData<ItemDetail>()
    var message: MutableLiveData<Int> = MutableLiveData<Int>()

    fun getDetailProduct(idProduct: String) {
        repositoryMeli.responseDetail(idProduct,
            { _, _ ->
                message.postValue(R.string.error_connection)
            },
            { _, response ->
                if (response.isSuccessful) {
                    if (response.body()!!.isNotEmpty()) {
                        itemDetailProduct.postValue(response.body())
                    } else {
                        message.postValue(R.string.no_detail_product)
                    }
                } else {
                    message.postValue(ErrorStatusCode.evaluateResponseCode(response.code()))
                }
            })
    }

    fun getItems(idItem: String) {
        repositoryMeli.responseGetItem(idItem,
            { _, _ ->
                message.postValue(R.string.error_connection)
            },
            { _, response ->
                if (response.isSuccessful) {
                    if (response.body()!!.pictures.isNotEmpty()) {
                        item.postValue(response.body()!!)
                    } else {
                        message.postValue(R.string.no_images)
                    }
                } else {
                    message.postValue(ErrorStatusCode.evaluateResponseCode(response.code()))
                }
            })
    }
}