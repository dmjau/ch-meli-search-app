package com.cursoandroid.melisearchapp.ui.home

import android.content.Context
import android.content.res.Configuration
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cursoandroid.melisearchapp.R
import com.cursoandroid.melisearchapp.adapters.PublicationAdapter
import com.cursoandroid.melisearchapp.common.ConnectivityCheck
import com.google.android.material.snackbar.Snackbar
/*
 * Activity del home.
 */
class HomeActivity : AppCompatActivity() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var adapter: PublicationAdapter
    private var snackbar: Snackbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        setObservers()
        val searchProduct: EditText = findViewById(R.id.search_product)
        setSearchProduct(searchProduct)
    }

    private fun setSearchProduct(searchProduct: EditText) {
        var inputMethod: InputMethodManager
        if(application.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            searchProduct.width = 900
        }
        searchProduct.setOnKeyListener((View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                inputMethod = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                getProduct(searchProduct.text.toString())
                inputMethod.hideSoftInputFromWindow(v.applicationWindowToken, 0)
                return@OnKeyListener true
            }
            false
        }))
    }

    private fun getProduct(product: String) {
        val container: View = findViewById(R.id.containerRecyclerView)
        snackbar = Snackbar.make(container, getString(R.string.loading), Snackbar.LENGTH_LONG)
        if (ConnectivityCheck.verifyConnection(applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)) {
            snackbar!!.show()
            homeViewModel.searchProduct(product)
        } else {
            Toast.makeText(applicationContext, getString(R.string.no_connection), Toast.LENGTH_LONG).show()
        }
    }

    private fun setObservers() {
        homeViewModel.message.observe(this, Observer {
            Toast.makeText(applicationContext, it, Toast.LENGTH_LONG).show()
        })

        homeViewModel.productList.observe(this, Observer {
            val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
            adapter = PublicationAdapter()
            recyclerView.layoutManager = LinearLayoutManager(applicationContext)
            recyclerView.adapter = adapter
            adapter.setDataList(it.results)
            adapter.notifyDataSetChanged()
            if(snackbar != null){
                snackbar!!.dismiss()
            }
        })
    }
}