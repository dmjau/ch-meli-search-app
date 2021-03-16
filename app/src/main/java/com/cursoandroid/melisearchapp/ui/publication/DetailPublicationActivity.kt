package com.cursoandroid.melisearchapp.ui.publication

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.cursoandroid.melisearchapp.R
import com.cursoandroid.melisearchapp.adapters.PictureAdapter
import com.cursoandroid.melisearchapp.common.ConnectivityCheck
import com.cursoandroid.melisearchapp.common.Constants
import com.cursoandroid.melisearchapp.data.models.Attribute
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import java.text.NumberFormat
import java.util.*
import kotlin.math.roundToInt

class DetailPublicationActivity : AppCompatActivity() {

    private lateinit var detailPublicationViewModel: DetailPublicationViewModel
    private  var snackbar: Snackbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_detail_product)

        detailPublicationViewModel = ViewModelProvider(this).get(DetailPublicationViewModel::class.java)

        setObservers()

        val iconBack: ImageButton = findViewById(R.id.ic_back)

        setBackButton(iconBack)

        validateIntent()
    }

    private fun validateIntent() {
        if (intent.extras!!.containsKey(Constants.PARAM_PUB)) {
            val detailPublication: Attribute =
                Gson().fromJson(
                    intent.extras!!.getString(Constants.PARAM_PUB)
                    , Attribute::class.java
                )
            populatePublication(detailPublication)
        }
    }

    private fun setBackButton(iconBack: ImageButton) {
        iconBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun populatePublication(detailPublication: Attribute) {
        val titleProduct: TextView = findViewById(R.id.title_product)
        val format = NumberFormat.getCurrencyInstance(Locale.getDefault())
        var price = format.format(detailPublication.price.roundToInt())
        val priceProduct: TextView = findViewById(R.id.price_product)

        titleProduct.text = detailPublication.title
        format.currency = Currency.getInstance(detailPublication.currency_id)
        price = price.replace(",00", "").replace("ARS", "")
        priceProduct.text = price

        if (ConnectivityCheck.verifyConnection(applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)) {
            val container: View = findViewById(R.id.containerDetailProduct)
            snackbar = Snackbar.make(
                container,
                getString(R.string.loading),
                Snackbar.LENGTH_LONG
            )

            snackbar!!.show()
            setCondition(detailPublication.condition, detailPublication.sold_quantity)
            setDescription(detailPublication.id)
            setCarousel(detailPublication.id)

        } else {
            Toast.makeText(
                applicationContext,
                getString(R.string.no_connection),
                Toast.LENGTH_LONG
            ).show()
        }

    }

    private fun setCondition(conditionPublication: String, soldQuantity: Int) {
        val condition : TextView = findViewById(R.id.condition)
        if (conditionPublication == "new") {
            condition.text = "Nuevo - $soldQuantity vendidos"
        } else {
            condition.text = "$soldQuantity vendidos"
        }
    }

    private fun setDescription(idPublication: String) {
        if (detailPublicationViewModel.itemDetailProduct.value == null) {
            detailPublicationViewModel.getDetailProduct(idPublication)
        }
    }

    private fun setCarousel(idPublication: String) {
        if (detailPublicationViewModel.item.value == null) {
            detailPublicationViewModel.getItems(idPublication)
        }
    }

    private fun setObservers() {

        detailPublicationViewModel.message.observe(this, Observer {
            Toast.makeText(applicationContext,getString(it), Toast.LENGTH_LONG)
        })

        detailPublicationViewModel.item.observe(this, Observer {
            val imagesProduct: ViewPager = findViewById(R.id.image_product)
            val pictureAdapter = PictureAdapter(it.pictures)

            imagesProduct.setAdapter(pictureAdapter).also {
                if(snackbar != null){
                    snackbar!!.dismiss()
                }
            }
        })

        detailPublicationViewModel.itemDetailProduct.observe(this, Observer {
            val descriptionProduct: TextView = findViewById(R.id.description_product)
            descriptionProduct.text = it[0].plain_text
        })
    }

}