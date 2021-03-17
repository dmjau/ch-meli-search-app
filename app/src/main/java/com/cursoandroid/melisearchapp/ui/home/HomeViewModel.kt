package com.cursoandroid.melisearchapp.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cursoandroid.melisearchapp.R
import com.cursoandroid.melisearchapp.common.ErrorStatusCode
import com.cursoandroid.melisearchapp.repository.RepositoryMeli
import com.cursoandroid.melisearchapp.data.models.InfoResponse
/*
 * ViewModel del home. LLamado al repositorio, y lo mantiene vigente para consultarlo mediante LiveData.
 */
class HomeViewModel() : ViewModel() {
    private var repositoryMeli: RepositoryMeli = RepositoryMeli()

    var message: MutableLiveData<Int> = MutableLiveData<Int>()
    val productList = MutableLiveData<InfoResponse>()

    fun searchProduct(product: String) {
        repositoryMeli.responseSearch(product,
            { _, _ -> message.postValue(R.string.error_connection) },
            { _, response ->
                if (response.isSuccessful) {
                    if(response.body()!!.results.isNotEmpty()) {
                        productList.postValue(response.body())
                    }else{
                        message.postValue(R.string.no_elements)
                    }
                }else{
                    message.postValue(ErrorStatusCode.evaluateResponseCode(response.code()))
                }
            })
    }

}